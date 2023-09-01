package zw.co.santech.productservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class InvalidUserRightsCustomException extends RuntimeException{
    private String errorCode;

    public InvalidUserRightsCustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
