package pl.zaliczto.simpay.responses.payment.transactioninfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AmountResponse {
    private BigDecimal value;
    private String currency;
    private BigDecimal commission;
    @JsonProperty("commission_currency")
    private String commissionCurrency;
    private OriginalAmountResponse original;
}
