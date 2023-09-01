package zw.co.santech.authenticationservice.configs;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import zw.co.santech.authenticationservice.enums.RolesEnum;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AuthenticatedUser extends AbstractAuthenticationToken {

    private String employeeId;

    private String emailAddress;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private RolesEnum roles;

    public AuthenticatedUser(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return roles;
    }

    @Override
    public Object getPrincipal() {
        return emailAddress;
    }
}
