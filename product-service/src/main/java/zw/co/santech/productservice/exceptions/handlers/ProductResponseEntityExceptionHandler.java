package zw.co.santech.productservice.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zw.co.santech.productservice.data.ErrorResponse;
import zw.co.santech.productservice.exceptions.ProductServiceCustomException;

@ControllerAdvice
public class ProductResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductServiceCustomException.class)
    public ResponseEntity<ErrorResponse>
    handleProductServiceException(ProductServiceCustomException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .errorCode(e.getErrorCode())
                .build(), HttpStatus.NOT_FOUND);
    }
}
