package pl.zaliczto.simpay.exceptions;

import java.util.Map;

public class ValidationFailedException extends SimPayException {
    private final Map<String, Object> validationErrors;

    public ValidationFailedException(Map<String, Object> validationErrors, String message) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public Map<String, Object> getValidationErrors() {
        return validationErrors;
    }
}

