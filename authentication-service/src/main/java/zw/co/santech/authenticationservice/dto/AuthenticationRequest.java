package zw.co.santech.authenticationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "The username cannot be missing")
    private String username;
    @NotBlank(message = "The password cannot be missing")
    private String password;
}
