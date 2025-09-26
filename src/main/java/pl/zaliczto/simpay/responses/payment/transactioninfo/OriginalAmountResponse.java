package pl.zaliczto.simpay.responses.payment.transactioninfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * The type Original amount response.
 */
@Data
public class OriginalAmountResponse {
    private BigDecimal value;
    private String currency;
    @JsonProperty("pln_rate")
    private String plnRate;
}
