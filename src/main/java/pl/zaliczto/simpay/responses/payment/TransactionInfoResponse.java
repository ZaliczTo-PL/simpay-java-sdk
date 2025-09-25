package pl.zaliczto.simpay.responses.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.zaliczto.simpay.enums.payment.TransactionStatus;
import pl.zaliczto.simpay.responses.payment.transactioninfo.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class TransactionInfoResponse {
    private String id;
    private TransactionStatus status;
    private AmountResponse amount;
    private String channel;
    private String control;
    private String description;
    private RedirectsResponse redirects;
    private CustomerResponse customer;
    private CustomerFullDataResponse billing;
    private CustomerFullDataResponse shipping;
    private List<CartItemResponse> cart;
    private Map<String, Object> additional;
    @JsonProperty("paid_at")
    private Instant paidAt;
    @JsonProperty("expires_at")
    private Instant expiresAt;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("updated_at")
    private Instant updatedAt;
    @JsonProperty("payer_transaction_id")
    private String payerTransactionId;
}

