package pl.zaliczto.simpay.responses.payment.ipn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.zaliczto.simpay.enums.payment.TransactionStatus;
import pl.zaliczto.simpay.enums.payment.BlikTicketStatus;

/**
 * Data object for type transaction_blik_level0:code_status_changed
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlikLevel0CodeStatusChangedData {
    @JsonProperty("ticket_status")
    private BlikTicketStatus ticketStatus;
    private Transaction transaction;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Transaction {
        private String id;
        @JsonProperty("payer_transaction_id")
        private String payerTransactionId;
        @JsonProperty("service_id")
        private String serviceId;
        private TransactionStatus status;
        private Amount amount;
        private String control; // nullable
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        @JsonProperty("final_currency")
        private String finalCurrency;
        @JsonProperty("final_value")
        private String finalValue;
        @JsonProperty("original_currency")
        private String originalCurrency;
        @JsonProperty("original_value")
        private String originalValue;
        @JsonProperty("commission_system")
        private String commissionSystem; // nullable
        @JsonProperty("commission_partner")
        private String commissionPartner; // nullable
        @JsonProperty("commission_currency")
        private String commissionCurrency; // nullable
    }
}
