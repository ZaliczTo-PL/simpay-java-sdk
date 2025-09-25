package pl.zaliczto.simpay.exceptions;

public class SimPayException extends Exception {
    private final String errorCode;

    public SimPayException(String message) {
        super(message);
        this.errorCode = null;
    }

    public SimPayException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public SimPayException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SimPayException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

