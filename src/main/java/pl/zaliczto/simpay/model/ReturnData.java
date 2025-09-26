package pl.zaliczto.simpay.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zaliczto.simpay.util.Jsons;

import java.util.Map;

/**
 * The type Return data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnData {
    private String success;
    private String failure;

    /**
     * To map.
     *
     * @return the map
     */
    public Map<String, Object> toMap() {
        return Jsons.MAPPER.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}

