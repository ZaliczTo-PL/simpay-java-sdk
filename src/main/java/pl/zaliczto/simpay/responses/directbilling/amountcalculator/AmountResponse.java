package pl.zaliczto.simpay.responses.directbilling.amountcalculator;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AmountResponse {
    private BigDecimal net;
    private BigDecimal gross;
}
