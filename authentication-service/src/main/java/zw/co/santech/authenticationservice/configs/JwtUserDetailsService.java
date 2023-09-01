package zw.co.santech.authenticationservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zw.co.santech.authenticationservice.models.ApplicationUser;
import zw.co.santech.authenticationservice.repositories.ApplicationUserRepository;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findApplicationUserByUserProfile_EmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!: " + username));
        return UserDetailsImpl.build(applicationUser);
    }
}
