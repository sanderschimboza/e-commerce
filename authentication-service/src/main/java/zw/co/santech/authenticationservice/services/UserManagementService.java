package zw.co.santech.authenticationservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.santech.authenticationservice.configs.AuthenticatedUser;
import zw.co.santech.authenticationservice.dto.ProfileDto;
import zw.co.santech.authenticationservice.enums.CommunicationMethod;
import zw.co.santech.authenticationservice.enums.RolesEnum;
import zw.co.santech.authenticationservice.enums.TwoFactorConfig;
import zw.co.santech.authenticationservice.exceptions.UserDataManipulationException;
import zw.co.santech.authenticationservice.models.ApplicationUser;
import zw.co.santech.authenticationservice.models.ProfileConfig;
import zw.co.santech.authenticationservice.models.UserProfile;
import zw.co.santech.authenticationservice.dto.UserProfileDto;
import zw.co.santech.authenticationservice.repositories.ApplicationUserRepository;
import zw.co.santech.authenticationservice.repositories.ProfileConfigRepository;
import zw.co.santech.authenticationservice.repositories.UserProfileRepository;
import zw.co.santech.authenticationservice.utils.Constants;
import zw.co.santech.authenticationservice.utils.UserRoleManagement;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManagementService {
    private final ApplicationUserRepository applicationUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final ProfileConfigRepository profileConfigRepository;
    private final UserRoleManagement userRoleManagement;
    private final PasswordEncoder passwordEncoder;

    public void proceedEnabled(AuthenticatedUser authenticatedUser) {
        if (!authenticatedUser.getEnabled())
            throw new AccessDeniedException("This profile is disabled! Access cannot be granted.");
    }
    public void authorizeUserCreationRequest(AuthenticatedUser user, ProfileDto profile, boolean userNotExists) {
        if (!userRoleManagement.validateCreationRights(user.getRoles())) {
            throw new UserDataManipulationException("Access Denied!. Insufficient user rights detected.");
        }
        if (userNotExists && applicationUserRepository.existsByUserProfile(new UserProfile(profile.getEmailAddress()))) {
            throw new UserDataManipulationException("Operation Denied!. Requested user is already present: "
                    + profile.getEmailAddress());
        }
        if (!userNotExists && !applicationUserRepository.existsByUserProfile(new UserProfile(profile.getEmailAddress()))) {
            throw new UserDataManipulationException("Operation Denied!. Requested user does not exist: "
                    + profile.getEmailAddress());

        }
    }

    public String executeCreateUser(AuthenticatedUser authenticatedUser, UserProfileDto profile) {

        ProfileConfig profileConfig = ProfileConfig.builder()
                .communicationMethod(CommunicationMethod.BOTH)
                .twoFactorEnabled(Boolean.TRUE)
                .twoFactorConfig(TwoFactorConfig.EVERYTIME)
                .build();

        profileConfig = profileConfigRepository.save(profileConfig);

        UserProfile userProfile = UserProfile.builder()
                .emailAddress(profile.getEmailAddress())
                .employeeId(profile.getEmailAddress())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .enabled(profile.getEnabled())
                .profilePic(profile.getProfilePic())
                .userRole(RolesEnum.USER)
                .config(profileConfig)
                .build();

        userProfile = userProfileRepository.save(userProfile);
        profileConfig.setUserProfile(userProfile);
        profileConfigRepository.save(profileConfig);

        ApplicationUser applicationUser = ApplicationUser.builder()
                .userProfile(userProfile)
                .encryptedPass(passwordEncoder.encode(profile.getPassword()))
                .securityCode(Constants.generateSecurityCode())
                .expiresAt(LocalDateTime.now().plusHours(3))
                .securityToken("")
                .nextSecurityCodeCheck(LocalDateTime.now()
                        .plusHours(userProfile.getConfig().getTwoFactorConfig().getOffSet()))
                .build();

        applicationUserRepository.save(applicationUser);
        log.info("User {} successfully created a new user -> {}",
                authenticatedUser.getPrincipal(),
                profile.getEmailAddress());

        return userProfile.getEmployeeId();

    }
}
