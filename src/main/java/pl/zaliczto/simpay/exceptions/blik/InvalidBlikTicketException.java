package pl.zaliczto.simpay.exceptions.blik;

import pl.zaliczto.simpay.exceptions.SimPayException;

public class InvalidBlikTicketException extends SimPayException {
    public InvalidBlikTicketException(String message, String errorCode) {
        super(message, errorCode);
    }
}

