package pl.zaliczto.simpay.exceptions;

/**
 * The AuthorizationException is thrown when the HttpService receives a 401 or 403 status code.
 */
public class AuthorizationException extends SimPayException {
    /**
     * Instantiates a new Authorization exception.
     *
     * @param message the message
     */
    public AuthorizationException(String message) { super(message); }

    /**
     * Instantiates a new Authorization exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public AuthorizationException(String message, String errorCode) { super(message, errorCode); }
}

