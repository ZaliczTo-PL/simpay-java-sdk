package pl.zaliczto.simpay.enums.payment;

/**
 * Status of BLIK Level 0 ticket (code) sent in IPN notification transaction_blik_level0:code_status_changed.
 */
public enum BlikTicketStatus {
    VALID,
    REJECTED,
    EXPIRED,
    PENDING,
    UNKNOWN
}
