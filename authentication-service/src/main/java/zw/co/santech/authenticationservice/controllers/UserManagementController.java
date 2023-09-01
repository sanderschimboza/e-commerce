package zw.co.santech.authenticationservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.santech.authenticationservice.configs.AuthenticatedUser;
import zw.co.santech.authenticationservice.dto.UserProfileDto;
import zw.co.santech.authenticationservice.services.UserManagementService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(AuthenticatedUser authenticatedUser,
                                             @Valid @RequestBody UserProfileDto profileDto) {

        if (authenticatedUser == null)
            return new ResponseEntity<>("Token is Invalid!", HttpStatus.UNAUTHORIZED);

        userManagementService.proceedEnabled(authenticatedUser);
        userManagementService.authorizeUserCreationRequest(authenticatedUser, profileDto, true);
        String employeeId = userManagementService.executeCreateUser(authenticatedUser, profileDto);

        return new ResponseEntity<>(employeeId, HttpStatus.CREATED);
    }
}
