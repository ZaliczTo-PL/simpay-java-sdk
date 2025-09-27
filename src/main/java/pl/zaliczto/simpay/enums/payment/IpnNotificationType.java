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

    /**
     * Raw string value used in IPN payloads.
     * @return value
     */
    public String getValue() { return value; }

    /**
     * Resolve enum from raw value.
     * Returns null if not matched (for forward compatibility with new events).
     * @param value raw string value
     * @return enum or null
     */
    public static IpnNotificationType fromValue(String value) {
        if (value == null) return null;
        for (IpnNotificationType t : values()) {
            if (t.value.equals(value)) return t;
        }
        return null;
    }
}
