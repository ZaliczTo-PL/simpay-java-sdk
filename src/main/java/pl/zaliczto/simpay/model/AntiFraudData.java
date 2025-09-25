package pl.zaliczto.simpay.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AntiFraudData {

    @JsonProperty("useragent")
    private String userAgent;

    @JsonProperty("steamid")
    private String steamId64;

    @JsonProperty("mcusername")
    private String minecraftUsername;

    @JsonProperty("mcid")
    private String minecraftUuid;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Map<String, Object> toMap() {
        return MAPPER.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}
