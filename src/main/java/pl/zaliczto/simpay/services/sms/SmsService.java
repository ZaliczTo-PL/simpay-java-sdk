package pl.zaliczto.simpay.services.sms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.responses.sms.SmsCodeResponse;
import pl.zaliczto.simpay.responses.sms.SmsNumberResponse;
import pl.zaliczto.simpay.services.core.HttpService;
import pl.zaliczto.simpay.util.Jsons;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Sms service.
 */
public class SmsService extends HttpService {

    /**
     * Instantiates a new Sms service.
     *
     * @param client the client
     */
    public SmsService(SimPayClient client) {
        super(client);
    }

    /**
     * Numbers list.
     *
     * @return the list
     * @throws SimPayException the sim pay exception
     */
    public List<SmsNumberResponse> numbers() throws SimPayException {
        String serviceId = smsServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing SMS service identifier (smsServiceId)");
        }
        String path = String.format("sms/%s/numbers?limit=99", serviceId);
        ResponseEnvelope resp = request("GET", path, null);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || !data.isArray()) {
            return new ArrayList<>();
        }
        return Jsons.MAPPER.convertValue(data, new TypeReference<List<SmsNumberResponse>>(){});
    }

    /**
     * Check sms code response.
     *
     * @param code   the code
     * @param number the number
     * @return the sms code response
     * @throws SimPayException the sim pay exception
     */
    public SmsCodeResponse check(String code, Integer number) throws SimPayException {
        String serviceId = smsServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing SMS service identifier (smsServiceId)");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("code", code);
        if (number != null) payload.put("number", number);
        ResponseEnvelope resp = request("POST", String.format("sms/%s", serviceId), payload);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || data.isMissingNode()) {
            throw new SimPayException("Missing 'data' field in API response");
        }
        try {
            return Jsons.MAPPER.treeToValue(data, SmsCodeResponse.class);
        } catch (Exception e) {
            throw new SimPayException("Failed to map SmsCodeResponse", e);
        }
    }
}
