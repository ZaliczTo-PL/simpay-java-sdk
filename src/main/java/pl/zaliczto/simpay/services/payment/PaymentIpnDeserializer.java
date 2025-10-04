package pl.zaliczto.simpay.services.payment;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.zaliczto.simpay.enums.payment.IpnNotificationType;
import pl.zaliczto.simpay.responses.payment.PaymentIpnNotification;
import pl.zaliczto.simpay.responses.payment.ipn.*;
import pl.zaliczto.simpay.util.Jsons;

import java.io.IOException;

/**
 * Deserializes PaymentIpnNotification with typed data model depending on "type" field.
 */
public class PaymentIpnDeserializer extends JsonDeserializer<PaymentIpnNotification> {
    private static final ObjectMapper MAPPER = Jsons.MAPPER;

    @Override
    public PaymentIpnNotification deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode root = p.getCodec().readTree(p);
        PaymentIpnNotification base = new PaymentIpnNotification();
        if (root.hasNonNull("type")) base.setType(root.get("type").asText());
        if (root.hasNonNull("notification_id")) base.setNotificationId(root.get("notification_id").asText());
        if (root.hasNonNull("date")) base.setDate(java.time.Instant.parse(root.get("date").asText()));
        if (root.has("signature")) base.setSignature(root.get("signature").asText());
        JsonNode data = root.get("data");
        base.setData(data); // raw

        // map typed data
        IpnNotificationType type = IpnNotificationType.fromValue(base.getType());
        if (type != null && data != null && !data.isMissingNode()) {
            Object typed = switch (type) {
                case TRANSACTION_STATUS_CHANGED -> MAPPER.convertValue(data, TransactionStatusChangedData.class);
                case TRANSACTION_REFUND -> MAPPER.convertValue(data, TransactionRefundStatusChangedData.class);
                case TEST -> MAPPER.convertValue(data, TestNotificationData.class);
                case BLIK_LEVEL_0 -> MAPPER.convertValue(data, BlikLevel0CodeStatusChangedData.class);
            };
            base.setTypedData(typed);
        }
        return base;
    }
}
