package pl.zaliczto.simpay.responses.payment.servicechannel;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class  ChannelAmountResponse {
    private BigDecimal min;
    private BigDecimal max;
}
