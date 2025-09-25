package pl.zaliczto.simpay.services.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class PaymentServiceTest {

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
        String body = "{\"data\":{\"id\":\"tx1\",\"status\":\"transaction_paid\"}}";
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body).addHeader("Content-Type","application/json"));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).paymentServiceId("svc");
        var svc = client.getPaymentService();
        var info = svc.getTransactionInfo("tx1");
        assertThat(info.getId()).isEqualTo("tx1");
        assertThat(info.getStatus()).isNotNull();
    }

    @Test
    void getTransactionInfoRequiresServiceId() {
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString());
        var svc = client.getPaymentService();
        assertThatThrownBy(() -> svc.getTransactionInfo("tx1")).isInstanceOf(SimPayException.class)
                .hasMessageContaining("Missing payment service identifier");
    }
}
