package zw.co.santech.productservice.services.impl;

import org.springframework.stereotype.Service;
import zw.co.santech.productservice.enums.EnumRoles;
import zw.co.santech.productservice.exceptions.UserNotFoundCustomException;
import zw.co.santech.productservice.services.PermissionService;
import zw.co.santech.productservice.utils.Constants;

import java.util.Objects;

@Service
public class PermissionsServiceImpl implements PermissionService {
    @Override
    public void validateUser(String username, String roles) {

        if (Objects.isNull(username))
            throw new UserNotFoundCustomException(
                    "USER_NOT_FOUND",
                    "User was not found!"
            );

        if (!roles.equals(EnumRoles.SYS_ADMIN.name()))
            throw new UserNotFoundCustomException(
                    "INVALID_USER_RIGHTS",
                    Constants.INVALID_ADMIN_RIGHTS_MSG
            );
    }
}
