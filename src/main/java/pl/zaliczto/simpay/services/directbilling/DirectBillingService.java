package pl.zaliczto.simpay.services.directbilling;

import com.fasterxml.jackson.databind.JsonNode;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.responses.directbilling.AmountCalculatorResponse;
import pl.zaliczto.simpay.responses.directbilling.TransactionInfoResponse;
import pl.zaliczto.simpay.services.core.HttpService;
import pl.zaliczto.simpay.util.ArrayFlattener;
import pl.zaliczto.simpay.util.Hashes;
import pl.zaliczto.simpay.util.Jsons;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;

/**
 * The type Direct billing service.
 */
public class DirectBillingService extends HttpService {

    /**
     * Instantiates a new Direct billing service.
     *
     * @param client the client
     */
    public DirectBillingService(SimPayClient client) {
        super(client);
    }

    /**
     * Generate generate transaction.
     *
     * @return the generate transaction
     */
    public GenerateTransaction generate() { return new GenerateTransaction(client); }

    /**
     * Gets transaction info.
     *
     * @param transactionId the transaction id
     * @return the transaction info
     * @throws SimPayException the sim pay exception
     */
    public TransactionInfoResponse getTransactionInfo(String transactionId) throws SimPayException {
        String serviceId = directBillingServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing DirectBilling service identifier (directBillingServiceId)");
        }
        ResponseEnvelope resp = request("GET", String.format("directbilling/%s/transactions/%s", serviceId, transactionId), null);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || data.isMissingNode()) {
            throw new SimPayException("Missing 'data' field in API response");
        }
        try {
            return Jsons.MAPPER.treeToValue(data, TransactionInfoResponse.class);
        } catch (Exception e) {
            throw new SimPayException("Nie udało się zmapować odpowiedzi TransactionInfo (DB)", e);
        }
    }

    /**
     * Calculate amount calculator.
     *
     * @param amount the amount
     * @return the amount calculator response
     * @throws SimPayException the sim pay exception
     */
    public AmountCalculatorResponse calculate(BigDecimal amount) throws SimPayException {
        String serviceId = directBillingServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing DirectBilling service identifier (directBillingServiceId)");
        }
        String path = String.format("directbilling/%s/calculate?amount=%s", serviceId, amount);
        ResponseEnvelope resp = request("GET", path, null);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || data.isMissingNode()) {
            throw new SimPayException("Missing 'data' field in API response");
        }
        try {
            return Jsons.MAPPER.treeToValue(data, AmountCalculatorResponse.class);
        } catch (Exception e) {
            throw new SimPayException("Failed to map DirectBilling amount calculator response", e);
        }
    }

    /**
     * Checks if the ipn signature is valid.
     *
     * @param params the params
     * @return the boolean
     */
    public boolean ipnSignatureValid(Map<String, Object> params) {
        Object sig = params.get("signature");
        if (!(sig instanceof String signature) || signature.isEmpty()) return false;
        List<String> values = ArrayFlattener.flattenValues(
                params.entrySet().stream()
                        .filter(e -> !"signature".equals(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, java.util.LinkedHashMap::new))
        );
        values.add(directBillingHash());
        String calculated = Hashes.sha256Hex(String.join("|", values));
        return Hashes.constantTimeEquals(calculated, signature);
    }
}
