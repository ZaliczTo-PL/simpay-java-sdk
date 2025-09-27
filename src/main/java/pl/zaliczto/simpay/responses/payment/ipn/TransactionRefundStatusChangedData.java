package pl.zaliczto.simpay.responses.payment.ipn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.zaliczto.simpay.enums.payment.RefundStatus;

/**
 * Data object for type transaction_refund:status_changed
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRefundStatusChangedData {
    private String id; // refund id
    @JsonProperty("service_id")
    private String serviceId;
    private RefundStatus status;
    private Amount amount;
    private Transaction transaction;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        private String currency;
        private String value;
        @JsonProperty("wallet_currency")
        private String walletCurrency;
        @JsonProperty("wallet_value")
        private String walletValue;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Transaction {
        private String id; // transaction id
        @JsonProperty("payment_channel")
        private String paymentChannel;
        @JsonProperty("payment_type")
        private String paymentType;
    }
}
