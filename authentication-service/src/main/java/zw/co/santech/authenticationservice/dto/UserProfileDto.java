package zw.co.santech.authenticationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.santech.authenticationservice.dto.ProfileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto extends ProfileDto {

    @NotBlank(message = "The password cannot be missing")
    @Size(min = 6, max = 64, message = "The password must be correctly formulated")
    private String password;

    @NotBlank(message = "Employee ID cannot be missing")
    private String employeeId;

    @NotBlank(message = "First Name cannot be missing")
    private String firstName;

    @NotBlank(message = "Last Name cannot be missing")
    private String lastName;

    private String profilePic;

    @NotBlank(message = "Phone Number cannot be missing")
    @Size(min = 5, max = 32, message = "Phone Number must be correctly formulated")
    private String phoneNumber;

    @NotNull(message = "Enabling status cannot be missing")
    private Boolean enabled;

}
