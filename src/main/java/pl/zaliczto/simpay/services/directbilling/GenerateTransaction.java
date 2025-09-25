package pl.zaliczto.simpay.services.directbilling;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.enums.directbilling.AmountType;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.model.ReturnData;
import pl.zaliczto.simpay.responses.TransactionGeneratedResponse;
import pl.zaliczto.simpay.services.core.HttpService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class GenerateTransaction extends HttpService {

    private BigDecimal amount;
    private AmountType amountType = AmountType.Gross;
    private String description;
    private String control;
    private ReturnData returns;
    private String phoneNumber;
    private String steamId;
    private String email;

    public GenerateTransaction(SimPayClient client) {
        super(client);
    }

    public GenerateTransaction setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
        return this;
    }

    public TransactionGeneratedResponse generate() throws SimPayException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SimPayException("Amount must be set and greater than zero before generating a transaction");
        }
        String serviceId = directBillingServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing DirectBilling service identifier (directBillingServiceId)");
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("amount", amount);
        payload.put("amountType", amountType.getValue());
        if (description != null) payload.put("description", description);
        if (control != null) payload.put("control", control);
        if (returns != null) payload.put("returns", returns);
        if (phoneNumber != null) payload.put("phoneNumber", phoneNumber);
        if (steamId != null) payload.put("steamid", steamId);
        if (email != null) payload.put("email", email);

        ResponseEnvelope resp = request("POST", String.format("directbilling/%s/transactions", serviceId), payload);
        var data = resp.json() != null ? resp.json().path("data") : null;
        if (data == null || data.isMissingNode()) {
            throw new SimPayException("Missing 'data' field in API response");
        }
        String url = data.path("redirectUrl").asText(null);
        String txId = data.path("transactionId").asText(null);
        if (url == null || txId == null) {
            throw new SimPayException("Missing 'redirectUrl' or 'transactionId' in API response");
        }
        return new TransactionGeneratedResponse(url, txId);
    }
}
