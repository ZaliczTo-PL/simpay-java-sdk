package pl.zaliczto.simpay.enums.payment;

/**
 *  The enum Ipn notifcation type.
 */
public enum IpnNotificationType {

    BLIK_LEVEL_0("transaction_blik_level0:code_status_changed"),
    TEST("ipn:test"),
    TRANSACTION_REFUND("transaction_refund:status_changed"),
    TRANSACTION_STATUS_CHANGED("transaction:status_changed");

    private final String value;
    IpnNotificationType(String value) { this.value = value; }

}
