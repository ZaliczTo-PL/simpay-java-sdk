package pl.zaliczto.simpay.enums.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionStatus {
    New("transaction_new"),
    Generated("transaction_generated"),
    Confirmed("transaction_confirmed"),
    Paid("transaction_paid"),
    Failure("transaction_failure"),
    Expired("transaction_expired"),
    Refunded("transaction_refunded");

    private final String value;

    TransactionStatus(String value) { this.value = value; }
    @JsonValue public String getValue() { return value; }

    @JsonCreator
    public static TransactionStatus from(String v) {
        for (TransactionStatus s : values()) if (s.value.equalsIgnoreCase(v)) return s;
        throw new IllegalArgumentException("Unknown transaction status: " + v);
    }
}
