package pl.zaliczto.simpay.responses.payment.ipn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Data object for type ipn:test
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestNotificationData {
    @JsonProperty("service_id")
    private String serviceId;
    private String nonce;
}
