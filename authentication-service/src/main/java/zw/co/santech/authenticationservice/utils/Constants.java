package zw.co.santech.authenticationservice.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class Constants {

    private static final int SECURITY_CODE_LENGTH = 9;

    private static final int LOWER_BOUND = 65;

    private static final int UPPER_BOUND = 91;

    public static final String BEARER = "Bearer ";

    public static final String HEADER = "Authorization";

    public static final String SECURITY_CODE = "SECURITYCODE";

    public static final String TOKEN_SPLITTER = "\\.";

    public static final int TOKEN_PAYLOAD = 1;

    public static final String FORBIDDEN_ERROR_MESSAGE = "Not allowed to access the requested resource";

    public static final String DEVICE_TOKEN_MESSAGE = "Fetching device tokens by Email Address {}: Successful";

    public static String generateSecurityCode() {
        SecureRandom random = new SecureRandom();
        return random.ints(LOWER_BOUND, UPPER_BOUND)
                .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i))
                .limit(SECURITY_CODE_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
