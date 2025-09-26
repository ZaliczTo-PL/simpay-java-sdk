package pl.zaliczto.simpay.exceptions;

import lombok.Getter;

/**
 * The SimPayException is the base exception for all exceptions thrown by the SimPay SDK.
 */
@Getter
public class SimPayException extends Exception {

    private final String errorCode;

    /**
     * Instantiates a new Sim pay exception.
     *
     * @param message the message
     */
    public SimPayException(String message) {
        super(message);
        this.errorCode = null;
    }

    /**
     * Instantiates a new Sim pay exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public SimPayException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    /**
     * Instantiates a new Sim pay exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public SimPayException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Sim pay exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     */
    public SimPayException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}

