package pl.zaliczto.simpay.enums.payment;

/**
 * Status of BLIK Level 0 ticket (code) sent in IPN notification transaction_blik_level0:code_status_changed.
 */
public enum BlikTicketStatus {
    VALID,
    REJECTED,
    EXPIRED,
    INSUFFICIENT_FUNDS,
    LIMIT_EXCEEDED,
    TIMEOUT,
    GENERAL_ERROR,
    SYSTEM_ERROR,
    SEC_DECLINED,
    USER_DECLINED,
    TAS_DECLINED,
    PENDING,
    UNKNOWN;

    public static BlikTicketStatus fromRaw(String raw) {
        if (raw == null) return UNKNOWN;
        try { return BlikTicketStatus.valueOf(raw); } catch (IllegalArgumentException e) { return UNKNOWN; }
    }
}
