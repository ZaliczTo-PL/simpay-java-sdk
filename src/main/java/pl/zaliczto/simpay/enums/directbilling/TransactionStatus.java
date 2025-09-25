package pl.zaliczto.simpay.enums.directbilling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionStatus {
    New("transaction_db_new"),
    Confirmed("transaction_db_confirmed"),
    Paid("transaction_db_payed"),
    Rejected("transaction_db_rejected"),
    Cancelled("transaction_db_canceled"),
    GenerateError("transaction_db_generate_error");

    private final String value;
    TransactionStatus(String value) { this.value = value; }
    @JsonValue public String getValue() { return value; }

    @JsonCreator
    public static TransactionStatus from(String v) {
        for (TransactionStatus s : values()) if (s.value.equalsIgnoreCase(v)) return s;
        throw new IllegalArgumentException("Unknown DirectBilling status: " + v);
    }
}

