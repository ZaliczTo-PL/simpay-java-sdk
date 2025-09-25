package pl.zaliczto.simpay.services.directbilling;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class DirectBillingServiceTest {

    private MockWebServer server;

    @BeforeEach
    void setup() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void cleanup() throws IOException {
        server.shutdown();
    }

    @Test
    void getTransactionInfoParsesData() throws Exception {
        String body = "{\"data\":{\"id\":\"tx1\",\"status\":\"transaction_db_confirmed\"}}";
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body).addHeader("Content-Type","application/json"));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).directBillingServiceId("svc");
        var svc = client.getDirectBillingService();
        var info = svc.getTransactionInfo("tx1");
        assertThat(info.getId()).isEqualTo("tx1");
        assertThat(info.getStatus()).isNotNull();
    }

    @Test
    void getTransactionInfoRequiresServiceId() {
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString());
        var svc = client.getDirectBillingService();
        assertThatThrownBy(() -> svc.getTransactionInfo("tx1")).isInstanceOf(SimPayException.class)
                .hasMessageContaining("Missing DirectBilling service identifier");
    }
}
