package pl.zaliczto.simpay.responses.payment.servicechannel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChannelResponse {
    private String id;
    private String name;
    private String type;
    private String img;
    private BigDecimal commission;
    private List<String> currencies;
    private ChannelAmountResponse amount;
}
