package zw.co.santech.cartservice.exceptions.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import zw.co.santech.cartservice.enums.ExceptionMessage;
import zw.co.santech.cartservice.exceptions.CartServiceCustomException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        int responseCode = HttpStatus.valueOf(response.status()).value();

        log.info(String.valueOf(responseCode));

        return switch (responseCode) {
            case 500 -> new CartServiceCustomException("An internal server error from remote server occurred!",
                    ExceptionMessage.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR);
            case 401 -> new CartServiceCustomException("Unauthorized Request!",
                    ExceptionMessage.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            case 404 -> new CartServiceCustomException("Requested resource was not found from remote server!",
                    ExceptionMessage.NOT_FOUND, HttpStatus.NOT_FOUND);
            case 400 -> new CartServiceCustomException("Bad Request Error Returned!",
                    ExceptionMessage.BAD_REQUEST, HttpStatus.BAD_REQUEST);
            default -> new CartServiceCustomException("An exception was returned from remote server!",
                    ExceptionMessage.SERVICE_UNAVAILABLE, HttpStatus.valueOf(response.status()));
        };
    }
}
