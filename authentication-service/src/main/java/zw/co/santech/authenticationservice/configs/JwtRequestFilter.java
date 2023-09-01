package zw.co.santech.authenticationservice.configs;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import zw.co.santech.authenticationservice.models.UserProfile;
import zw.co.santech.authenticationservice.repositories.UserProfileRepository;
import zw.co.santech.authenticationservice.utils.Constants;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtManager jwtManager;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);

            log.info("Received this token: {}",token);

            if (!(Objects.isNull(token)) && jwtManager.validateJwtToken(token)) {
                String username = jwtManager.getUsernameFromToken(token);
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                UserProfile profile = userProfileRepository.findByEmailAddress(username);

                if (Objects.isNull(profile))
                    throw new AuthException("Failed confirm the requested user!");

                AuthenticatedUser authenticatedUser = new AuthenticatedUser(userDetails.getAuthorities());
                authenticatedUser.setAuthenticated(true);
                authenticatedUser.setDetails(profile.getEmailAddress());
                authenticatedUser.setEmailAddress(profile.getEmailAddress());
                authenticatedUser.setEmployeeId(profile.getEmployeeId());
                authenticatedUser.setFirstName(profile.getFirstName());
                authenticatedUser.setLastName(profile.getLastName());
                authenticatedUser.setPhoneNumber(profile.getPhoneNumber());
                authenticatedUser.setEnabled(profile.getEnabled());
                authenticatedUser.setRoles(profile.getUserRole());
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

                log.info("Authenticated User is: {}", authenticatedUser);
            }
        } catch (Exception ex) {
            log.error("Could not process authenticated user flow!");
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(Constants.HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constants.BEARER)) {
            return headerAuth.substring(Constants.BEARER.length());
        }
        return null;
    }
}
