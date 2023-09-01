package zw.co.santech.cartservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.santech.cartservice.enums.ExceptionMessage;

@EqualsAndHashCode(callSuper = false)
@Data
public class ResourceNotFoundCustomException extends RuntimeException {
    private ExceptionMessage errorCode;

    public ResourceNotFoundCustomException(String message, ExceptionMessage errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
