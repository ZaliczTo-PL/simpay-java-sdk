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

/**
 * The type Payment service.
 */
public class PaymentService extends HttpService {

    /**
     * Instantiates a new Payment service.
     *
     * @param client the client
     */
    public PaymentService(SimPayClient client) {
        super(client);
    }

    /**
     * Gets generate transaction.
     *
     * @return the generate transaction
     */
    public GenerateTransaction generate() {
        return new GenerateTransaction(client);
    }

    /**
     * Gets transaction info.
     *
     * @param transactionId the transaction id
     * @return the transaction info
     * @throws SimPayException the sim pay exception
     */
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

    /**
     * Channels list.
     *
     * @return the list
     * @throws SimPayException the sim pay exception
     */
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

    /**
     * Currencies list.
     *
     * @return the list
     * @throws SimPayException the sim pay exception
     */
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

    /**
     * Blik level 0 initiate blik level 0.
     *
     * @return the initiate blik level 0
     */
    public InitiateBlikLevel0 blikLevel0() {
        return new InitiateBlikLevel0(client);
    }

    /**
     * Ipn signature valid boolean.
     *
     * @param params the params
     * @return the boolean
     */
    public boolean ipnSignatureValid(Map<String, Object> params) {
        Object sig = params.get("signature");
        if (!(sig instanceof String signature) || signature.isEmpty()) return false;
        // IPN v2 spec (https://docs.simpay.pl/notifications/payment):
        // signature = sha256(type|notification_id|date|<flattened data values>|key)
        Map<String, Object> filtered = params.entrySet().stream()
                .filter(e -> !"signature".equals(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, java.util.LinkedHashMap::new));
        List<String> values = new ArrayList<>();
        Object type = filtered.remove("type");
        Object notificationId = filtered.remove("notification_id");
        Object date = filtered.remove("date");
        Object data = filtered.remove("data");
        if (type != null) values.add(String.valueOf(type));
        if (notificationId != null) values.add(String.valueOf(notificationId));
        if (date != null) values.add(String.valueOf(date));
        if (data != null) values.addAll(ArrayFlattener.flattenValues(data));
        if (!filtered.isEmpty()) {
            values.addAll(ArrayFlattener.flattenValues(filtered));
        }
        values.add(client.paymentHash());
        String calculated = Hashes.sha256Hex(String.join("|", values));
        return Hashes.constantTimeEquals(calculated, signature);
    }
}
