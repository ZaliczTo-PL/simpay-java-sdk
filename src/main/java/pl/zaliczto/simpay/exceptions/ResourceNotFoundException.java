package pl.zaliczto.simpay.exceptions;

/**
 * The ResourceNotFoundException is thrown when the HttpService receives a 404 status code.
 */
public class ResourceNotFoundException extends SimPayException {
    /**
     * Instantiates a new Resource not found exception.
     *
     * @param message the message
     */
    public ResourceNotFoundException(String message) { super(message); }
}

