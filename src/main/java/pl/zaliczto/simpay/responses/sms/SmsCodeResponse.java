package pl.zaliczto.simpay.responses.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.math.BigDecimal;

/**
 * The type Sms code response.
 */
@Data
public class SmsCodeResponse {
    private boolean used;
    private String code;
    private boolean test;
    private String from;
    private int number;
    private BigDecimal value;
    @JsonProperty("used_at")
    private Instant usedAt;
}
