package pl.zaliczto.simpay.enums.directbilling;

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
    New("transaction_db_new"),
    /**
     * Confirmed transaction status.
     */
    Confirmed("transaction_db_confirmed"),
    /**
     * Paid transaction status.
     */
    Paid("transaction_db_payed"),
    /**
     * Rejected transaction status.
     */
    Rejected("transaction_db_rejected"),
    /**
     * Cancelled transaction status.
     */
    Cancelled("transaction_db_canceled"),
    /**
     * Generate error transaction status.
     */
    GenerateError("transaction_db_generate_error");

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
        throw new IllegalArgumentException("Unknown DirectBilling status: " + v);
    }
}

