package zw.co.santech.cartservice.exceptions;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import zw.co.santech.cartservice.enums.ExceptionMessage;

@EqualsAndHashCode(callSuper = true)
@Data
public class CartServiceCustomException extends RuntimeException {
    private ExceptionMessage errorCode;
    @JsonIgnore
    private HttpStatus httpStatus;
    public CartServiceCustomException(String message, ExceptionMessage errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public CartServiceCustomException(String message, ExceptionMessage errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
