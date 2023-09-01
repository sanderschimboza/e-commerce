package zw.co.santech.authenticationservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zw.co.santech.authenticationservice.configs.JwtManager;
import zw.co.santech.authenticationservice.dto.AuthenticationRequest;
import zw.co.santech.authenticationservice.dto.AuthenticationResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationManagerService {

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getUsername(), authenticationRequest.getPassword()
                        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtManager.generateToken(authentication);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
