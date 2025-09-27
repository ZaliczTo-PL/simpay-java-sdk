package pl.zaliczto.simpay.enums.payment;

/**
 * Error codes returned when submitting a BLIK Level 0 code (initiation step) according to docs.
 */
public enum BlikCodeError {
    INVALID_BLIK_CODE,
    PAYER_APP_NOT_ACTIVE,
    PAYER_APP_NOT_FOUND,
    INVALID_BLIK_CODE_FORMAT,
    BLIK_CODE_EXPIRED,
    BLIK_CODE_LIMIT,
    BLIK_CODE_CANCELLED,
    BLIK_CODE_NOT_SUPPORTED,
    BLIK_CODE_USED,
    BLIK_GENERAL_ERROR,
    BLIK_TECHNICAL_BREAK,
    INSUFFICIENT_FUNDS,
    LIMIT_EXCEEDED,
    TIMEOUT,
    GENERAL_ERROR,
    SYSTEM_ERROR,
    SEC_DECLINED,
    USER_DECLINED,
    TAS_DECLINED;

    public static BlikCodeError from(String raw) {
        if (raw == null) return null;
        try { return BlikCodeError.valueOf(raw); } catch (IllegalArgumentException e) { return null; }
    }
}
