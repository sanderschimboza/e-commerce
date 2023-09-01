package zw.co.santech.productservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundCustomException extends RuntimeException {
    private String errorCode;

    public UserNotFoundCustomException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }
}
