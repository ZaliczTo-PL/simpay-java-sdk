package pl.zaliczto.simpay.responses.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * The type Sms number response.
 */
@Data
public class SmsNumberResponse {
    private int number;
    private BigDecimal value;
    @JsonProperty("value_gross")
    private BigDecimal valueGross;
    private boolean adult;
}
