package pl.zaliczto.simpay.responses.directbilling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.zaliczto.simpay.enums.directbilling.AmountType;
import pl.zaliczto.simpay.enums.directbilling.MobileCarrier;
import pl.zaliczto.simpay.enums.directbilling.TransactionStatus;
import pl.zaliczto.simpay.responses.directbilling.transactioninfo.NotifyResponse;

import java.time.Instant;
import java.math.BigDecimal;

/**
 * The type Transaction info response.
 */
@Data
public class TransactionInfoResponse {
    private String id;
    private TransactionStatus status;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    private String control;
    private BigDecimal value;
    @JsonProperty("value_net")
    private BigDecimal valueNet;
    @JsonProperty("value_created")
    private BigDecimal valueCreated;
    @JsonProperty("value_created_type")
    private AmountType valueCreatedType;
    @JsonProperty("operator")
    private MobileCarrier carrier;
    private NotifyResponse notify;
    private Double score;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("updated_at")
    private Instant updatedAt;
}
