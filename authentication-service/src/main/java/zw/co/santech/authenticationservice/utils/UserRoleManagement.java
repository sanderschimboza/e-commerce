package zw.co.santech.authenticationservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.santech.authenticationservice.enums.RolesEnum;

@Slf4j
@Service
public class UserRoleManagement {

    private static final String INVALID_ROLE_ERROR = "Invalid role specified => {}";

    public boolean validateCreationRights(RolesEnum role) {
        try {
            if (role.equals(RolesEnum.SYS_ADMIN)) {
                return true;
            }
        } catch (IllegalArgumentException ex) {
            log.error(INVALID_ROLE_ERROR, ex.getLocalizedMessage());
        }
        return false;
    }

}
