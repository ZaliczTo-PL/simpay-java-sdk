package pl.zaliczto.simpay.services.payment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.responses.payment.TransactionInfoResponse;
import pl.zaliczto.simpay.responses.payment.servicechannel.ChannelResponse;
import pl.zaliczto.simpay.responses.payment.servicecurrency.CurrencyResponse;
import pl.zaliczto.simpay.services.core.HttpService;
import pl.zaliczto.simpay.util.ArrayFlattener;
import pl.zaliczto.simpay.util.Hashes;
import pl.zaliczto.simpay.util.Jsons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentService extends HttpService {

    public PaymentService(SimPayClient client) {
        super(client);
    }

    public GenerateTransaction generate() {
        return new GenerateTransaction(client);
    }

    public TransactionInfoResponse getTransactionInfo(String transactionId) throws SimPayException {
        String serviceId = paymentServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing payment service identifier (paymentServiceId)");
        }
        ResponseEnvelope resp = request("GET", String.format("payment/%s/transactions/%s", serviceId, transactionId), null);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || data.isMissingNode()) {
            throw new SimPayException("Missing 'data' field in API response");
        }
        try {
            return Jsons.MAPPER.treeToValue(data, TransactionInfoResponse.class);
        } catch (Exception e) {
            throw new SimPayException("Failed to map TransactionInfoResponse", e);
        }
    }

    public List<ChannelResponse> channels() throws SimPayException {
        String serviceId = paymentServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing payment service identifier (paymentServiceId)");
        }
        ResponseEnvelope resp = request("GET", String.format("payment/%s/channels", serviceId), null);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || !data.isArray()) {
            return new ArrayList<>();
        }
        return Jsons.MAPPER.convertValue(data, new TypeReference<List<ChannelResponse>>(){});
    }

    public List<CurrencyResponse> currencies() throws SimPayException {
        String serviceId = paymentServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing payment service identifier (paymentServiceId)");
        }
        ResponseEnvelope resp = request("GET", String.format("payment/%s/currencies", serviceId), null);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || !data.isArray()) {
            return new ArrayList<>();
        }
        return Jsons.MAPPER.convertValue(data, new TypeReference<List<CurrencyResponse>>(){});
    }

    public InitiateBlikLevel0 blikLevel0() {
        return new InitiateBlikLevel0(client);
    }

    public boolean ipnSignatureValid(Map<String, Object> params) {
        Object sig = params.get("signature");
        if (!(sig instanceof String signature) || signature.isEmpty()) return false;
        Map<String, Object> filtered = params.entrySet().stream()
                .filter(e -> !"signature".equals(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, java.util.LinkedHashMap::new));
        List<String> values = ArrayFlattener.flattenValues(filtered);
        values.add(client.paymentHash());
        String calculated = Hashes.sha256Hex(String.join("|", values));
        return Hashes.constantTimeEquals(calculated, signature);
    }
}
