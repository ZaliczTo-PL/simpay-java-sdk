package pl.zaliczto.simpay.exceptions;

import lombok.Getter;

import java.util.Map;

/**
 * The ValidationFailedException is thrown when the HttpService receives a 422 status code.
 */
@Getter
public class ValidationFailedException extends SimPayException {

    private final Map<String, Object> validationErrors;

    /**
     * Instantiates a new Validation failed exception.
     *
     * @param validationErrors the validation errors
     * @param message          the message
     */
    public ValidationFailedException(Map<String, Object> validationErrors, String message) {
        super(message);
        this.validationErrors = validationErrors;
    }

}

