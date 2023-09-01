package zw.co.santech.authenticationservice.exceptions;

public class UserDataManipulationException extends RuntimeException {
    public UserDataManipulationException(String message) {
        super(message);
    }
    public UserDataManipulationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
