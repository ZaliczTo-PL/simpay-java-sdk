package pl.zaliczto.simpay.responses.payment.servicecurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

/**
 * The type Currency response.
 */
@Data
public class CurrencyResponse {
    private String iso;
    @JsonProperty("pln_rate")
    private String plnRate;
    @JsonProperty("nbp_table")
    private String nbpTable;
    private String prefix;
    private String suffix;
    @JsonProperty("updated_at")
    private Instant updatedAt;
}

