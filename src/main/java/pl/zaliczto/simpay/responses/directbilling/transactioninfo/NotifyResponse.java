package pl.zaliczto.simpay.responses.directbilling.transactioninfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

/**
 * The type Notify response.
 */
@Data
public class NotifyResponse {
    @JsonProperty("is_send")
    private boolean isSend;
    @JsonProperty("last_send_at")
    private Instant lastSendAt;
    private int count;
}

