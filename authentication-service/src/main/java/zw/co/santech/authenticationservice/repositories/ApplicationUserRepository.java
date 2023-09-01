package zw.co.santech.authenticationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.santech.authenticationservice.models.ApplicationUser;
import zw.co.santech.authenticationservice.models.UserProfile;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    boolean existsByUserProfile(UserProfile userProfile);

    boolean existsByUserProfileAndSecurityCode(UserProfile userProfile, String securityCode);

    Optional<ApplicationUser> findByUserProfile(UserProfile userProfile);

    Optional<ApplicationUser> findApplicationUserByUserProfile_EmailAddress(String email);
}
