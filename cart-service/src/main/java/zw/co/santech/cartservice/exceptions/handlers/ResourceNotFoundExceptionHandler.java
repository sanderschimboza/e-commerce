package zw.co.santech.cartservice.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.santech.cartservice.data.ErrorResponse;
import zw.co.santech.cartservice.exceptions.ResourceNotFoundCustomException;

@ControllerAdvice
class ResourceNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundCustomException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundCustomException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode().name())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
