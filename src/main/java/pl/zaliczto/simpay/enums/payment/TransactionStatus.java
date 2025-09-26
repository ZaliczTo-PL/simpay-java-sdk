package pl.zaliczto.simpay.enums.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * The enum Transaction status.
 */
@Getter
public enum TransactionStatus {
    /**
     * New transaction status.
     */
    New("transaction_new"),
    /**
     * Generated transaction status.
     */
    Generated("transaction_generated"),
    /**
     * Confirmed transaction status.
     */
    Confirmed("transaction_confirmed"),
    /**
     * Paid transaction status.
     */
    Paid("transaction_paid"),
    /**
     * Failure transaction status.
     */
    Failure("transaction_failure"),
    /**
     * Expired transaction status.
     */
    Expired("transaction_expired"),
    /**
     * Refunded transaction status.
     */
    Refunded("transaction_refunded");

    private final String value;

    TransactionStatus(String value) { this.value = value; }


    /**
     * From transaction status.
     *
     * @param v the value
     * @return the transaction status
     */
    @JsonCreator
    public static TransactionStatus from(String v) {
        for (TransactionStatus s : values()) if (s.value.equalsIgnoreCase(v)) return s;
        throw new IllegalArgumentException("Unknown transaction status: " + v);
    }
}
