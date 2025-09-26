package pl.zaliczto.simpay.responses.directbilling.amountcalculator;

import lombok.Data;
import java.math.BigDecimal;

/**
 * The type Amount response.
 */
@Data
public class AmountResponse {
    private BigDecimal net;
    private BigDecimal gross;
}
