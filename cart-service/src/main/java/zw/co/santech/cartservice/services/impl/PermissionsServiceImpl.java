package zw.co.santech.cartservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.santech.cartservice.enums.ExceptionMessage;
import zw.co.santech.cartservice.exceptions.UnAuthorizedCustomException;
import zw.co.santech.cartservice.services.PermissionsService;

import java.util.Objects;

@Service
@Slf4j
public class PermissionsServiceImpl implements PermissionsService {
    @Override
    public void validateUser(String user) {
        if (Objects.isNull(user) || user.isEmpty()) {
            log.error("Invalid User Detected. Please authenticate to proceed.");
            throw new UnAuthorizedCustomException(
                    "Invalid User Detected. Please authenticate to proceed.",
                    ExceptionMessage.INVALID_USER
            );
        }
        log.info("USERID => {}", user);
    }
}
