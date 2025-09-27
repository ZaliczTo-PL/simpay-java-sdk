package pl.zaliczto.simpay.enums.payment;

/**
 * Refund status enum matching IPN v2 documentation.
 */
public enum RefundStatus {
    refund_new,
    refund_pending,
    refund_completed,
    refund_rejected,
    refund_failed
}
