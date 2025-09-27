package pl.zaliczto.simpay.responses.payment;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import pl.zaliczto.simpay.enums.payment.IpnNotificationType;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generic representation of Payment IPN (v2) notification payload.
 * Keeps ordering of unknown fields for signature validation.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = pl.zaliczto.simpay.services.payment.PaymentIpnDeserializer.class)
public class PaymentIpnNotification {
    private String type;
    @JsonProperty("notification_id")
    private String notificationId;
    private Instant date; // ISO 8601
    private JsonNode data; // raw data object - user can map based on type
    private Object typedData; // mapped object (one of ipn.* classes) if deserialized with custom deserializer
    private String signature;

    // collect any other top level fields (forward compatibility)
    private final Map<String,Object> others = new LinkedHashMap<>();
    @JsonAnySetter
    public void any(String k, Object v) {
        if (!"type".equals(k) && !"notification_id".equals(k) && !"date".equals(k) && !"data".equals(k) && !"signature".equals(k)) {
            others.put(k, v);
        }
    }

    public IpnNotificationType notificationTypeEnum() {
        return IpnNotificationType.fromValue(type);
    }
}
