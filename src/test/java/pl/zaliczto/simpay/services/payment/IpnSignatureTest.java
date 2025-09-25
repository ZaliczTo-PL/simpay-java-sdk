package pl.zaliczto.simpay.services.payment;

import org.junit.jupiter.api.Test;
import pl.zaliczto.simpay.SimPayClient;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IpnSignatureTest {

    @Test
    void ipnSignatureValidPositive() {
        SimPayClient client = new SimPayClient("t").paymentHash("secret").paymentServiceId("svc");
        PaymentService svc = new PaymentService(client);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("a", "1");
        params.put("b", "2");
        // signature of "1|2|secret"
        params.put("signature", pl.zaliczto.simpay.util.Hashes.sha256Hex("1|2|secret"));
        assertThat(svc.ipnSignatureValid(params)).isTrue();
    }

    @Test
    void ipnSignatureValidNegative() {
        SimPayClient client = new SimPayClient("t").paymentHash("secret").paymentServiceId("svc");
        PaymentService svc = new PaymentService(client);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("a", "1");
        params.put("signature", "deadbeef");
        assertThat(svc.ipnSignatureValid(params)).isFalse();
    }
}
