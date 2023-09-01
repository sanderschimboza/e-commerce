package zw.co.santech.authenticationservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.santech.authenticationservice.dto.AuthenticationRequest;
import zw.co.santech.authenticationservice.dto.AuthenticationResponse;
import zw.co.santech.authenticationservice.services.AuthenticationManagerService;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManagerService authenticationManagerService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationManagerService
                .authenticate(authenticationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
