package pl.zaliczto.simpay.services.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.enums.payment.BlikLevel0TicketType;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.exceptions.blik.InvalidBlikTicketException;
import pl.zaliczto.simpay.services.core.HttpService;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class InitiateBlikLevel0 extends HttpService {

    private BlikLevel0TicketType ticketType;
    private String ticket;
    private String transactionId;

    public InitiateBlikLevel0(SimPayClient client) {
        super(client);
    }

    public boolean generate() throws SimPayException {
        if (ticketType == null || ticket == null || transactionId == null) {
            throw new SimPayException("ticketType, ticket and transactionId are required");
        }
        String path = String.format("payment/%s/blik/level0/%s", client.paymentServiceId(), transactionId);
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> ticketObj = new HashMap<>();
        ticketObj.put(ticketType.getValue(), ticket);
        payload.put("ticket", ticketObj);

        ResponseEnvelope resp = requestRaw("POST", path, payload);
        int code = resp.status();
        if (code >= 200 && code < 300) {
            return true;
        }
        String errorCode = resp.json() != null && resp.json().has("errorCode")
                ? resp.json().path("errorCode").asText()
                : null;
        String message = resp.json() != null && resp.json().has("message")
                ? resp.json().path("message").asText()
                : resp.body();

        if (code == 400) {
            if ("INVALID_BLIK_CODE".equals(errorCode)
                    || "INVALID_BLIK_CODE_FORMAT".equals(errorCode)
                    || "BLIK_CODE_EXPIRED".equals(errorCode)
                    || "BLIK_CODE_CANCELLED".equals(errorCode)
                    || "BLIK_CODE_USED".equals(errorCode)
                    || "BLIK_CODE_NOT_SUPPORTED".equals(errorCode)) {
                throw new InvalidBlikTicketException("[" + errorCode + "] " + message, errorCode);
            }
        }
        throw new SimPayException("[" + (errorCode != null ? errorCode : code) + "] " + message, errorCode);
    }
}

