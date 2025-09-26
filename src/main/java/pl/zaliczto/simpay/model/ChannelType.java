package pl.zaliczto.simpay.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zaliczto.simpay.util.Jsons;

import java.util.Map;

/**
 * The type Channel type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelType {
    @Builder.Default
    @JsonProperty("blik")
    private boolean blik = true;
    @Builder.Default
    @JsonProperty("transfer")
    private boolean transfer = true;
    @Builder.Default
    @JsonProperty("cards")
    private boolean cards = true;
    @Builder.Default
    @JsonProperty("ewallets")
    private boolean ewallets = true;
    @Builder.Default
    @JsonProperty("paypal")
    private boolean paypal = true;
    @Builder.Default
    @JsonProperty("paysafe")
    private boolean paysafe = true;
    @Builder.Default
    @JsonProperty("latam")
    private boolean latam = true;

    /**
     * To map.
     *
     * @return the map
     */
    public Map<String, Object> toMap() {
        return Jsons.MAPPER.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}
