package pl.zaliczto.simpay.responses.directbilling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.zaliczto.simpay.responses.directbilling.amountcalculator.AmountResponse;

@Data
public class AmountCalculatorResponse {
    private AmountResponse orange;
    private AmountResponse play;
    @JsonProperty("t-mobile")
    private AmountResponse tMobile;
    private AmountResponse plus;
    private AmountResponse netia;
}

