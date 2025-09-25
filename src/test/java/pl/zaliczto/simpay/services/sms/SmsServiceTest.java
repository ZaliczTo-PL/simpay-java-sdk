package pl.zaliczto.simpay.services.sms;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class SmsServiceTest {

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
    void numbersParsesArray() throws Exception {
        String body = "{\"data\":[{\"number\":7055,\"value\":\"1.23\"}]}";
        server.enqueue(new MockResponse().setResponseCode(200).setBody(body).addHeader("Content-Type","application/json"));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).smsServiceId("svc");
        var svc = client.getSmsService();
        var list = svc.numbers();
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getNumber()).isEqualTo(7055);
    }

    @Test
    void checkRequiresServiceId() {
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString());
        var svc = client.getSmsService();
        assertThatThrownBy(() -> svc.check("code", null)).isInstanceOf(SimPayException.class)
                .hasMessageContaining("Missing SMS service identifier");
    }
}
