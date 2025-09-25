package pl.zaliczto.simpay.exceptions;

public class AuthorizationException extends SimPayException {
    public AuthorizationException(String message) { super(message); }
    public AuthorizationException(String message, String errorCode) { super(message, errorCode); }
}

