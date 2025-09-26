package pl.zaliczto.simpay.exceptions.blik;

import pl.zaliczto.simpay.exceptions.SimPayException;

/**
 * This exception is thrown when a provided BLIK ticket is invalid.
 */
public class InvalidBlikTicketException extends SimPayException {
    /**
     * Instantiates a new Invalid blik ticket exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public InvalidBlikTicketException(String message, String errorCode) {
        super(message, errorCode);
    }
}

