package pl.zaliczto.simpay.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.zaliczto.simpay.util.Jsons;

import java.util.Map;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("producer")
    private String producer;
    @JsonProperty("category")
    private String category;
    @JsonProperty("code")
    private String code;

    public Map<String, Object> toMap() {
        return Jsons.MAPPER.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}
