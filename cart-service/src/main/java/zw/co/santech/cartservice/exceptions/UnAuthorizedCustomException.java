package zw.co.santech.cartservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zw.co.santech.cartservice.enums.ExceptionMessage;

@EqualsAndHashCode(callSuper = false)
@Data
public class UnAuthorizedCustomException extends RuntimeException {
    private ExceptionMessage errorCode;

    public UnAuthorizedCustomException(String message, ExceptionMessage errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
