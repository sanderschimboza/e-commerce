package zw.co.santech.productservice.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.santech.productservice.data.ErrorResponse;
import zw.co.santech.productservice.exceptions.InvalidUserRightsCustomException;

@ControllerAdvice
public class InvalidUserRightsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidUserRightsCustomException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserRights(InvalidUserRightsCustomException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
