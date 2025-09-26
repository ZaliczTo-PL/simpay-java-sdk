package pl.zaliczto.simpay.responses.payment.servicechannel;

import lombok.Data;
import java.math.BigDecimal;

/**
 * The type Channel amount response.
 */
@Data
public class  ChannelAmountResponse {
    private BigDecimal min;
    private BigDecimal max;
}
