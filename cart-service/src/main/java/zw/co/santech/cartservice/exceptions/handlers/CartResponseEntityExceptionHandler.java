package zw.co.santech.cartservice.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.santech.cartservice.data.ErrorResponse;
import zw.co.santech.cartservice.exceptions.CartServiceCustomException;

import java.util.Objects;

@ControllerAdvice
class CartResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleCartServiceException(CartServiceCustomException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode().name())
                .build(), (Objects.isNull(exception.getHttpStatus()) ? HttpStatus.NOT_FOUND
                : exception.getHttpStatus()));
    }
}
