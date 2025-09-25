package pl.zaliczto.simpay.services.directbilling;

import org.junit.jupiter.api.Test;
import pl.zaliczto.simpay.SimPayClient;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IpnSignatureDbTest {

    @Test
    void ipnSignatureValidPositive() {
        SimPayClient client = new SimPayClient("t").directBillingHash("secret").directBillingServiceId("svc");
        DirectBillingService svc = new DirectBillingService(client);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("x", "A");
        params.put("y", "B");
        params.put("signature", pl.zaliczto.simpay.util.Hashes.sha256Hex("A|B|secret"));
        assertThat(svc.ipnSignatureValid(params)).isTrue();
    }

    @Test
    void ipnSignatureValidNegative() {
        SimPayClient client = new SimPayClient("t").directBillingHash("secret").directBillingServiceId("svc");
        DirectBillingService svc = new DirectBillingService(client);
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("x", "A");
        params.put("signature", "deadbeef");
        assertThat(svc.ipnSignatureValid(params)).isFalse();
    }
}
