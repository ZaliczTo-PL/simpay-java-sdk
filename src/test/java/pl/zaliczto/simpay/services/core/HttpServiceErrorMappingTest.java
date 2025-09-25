package pl.zaliczto.simpay.services.core;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.AuthorizationException;
import pl.zaliczto.simpay.exceptions.ResourceNotFoundException;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.exceptions.ValidationFailedException;
import pl.zaliczto.simpay.services.payment.PaymentService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class HttpServiceErrorMappingTest {

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
    void maps401ToAuthorizationException() {
        server.enqueue(new MockResponse().setResponseCode(401).setBody("{\"message\":\"Unauthorized\"}"));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).paymentServiceId("svc");
        var svc = client.getPaymentService();
        assertThatThrownBy(() -> svc.getTransactionInfo("x")).isInstanceOf(AuthorizationException.class);
    }

    @Test
    void maps404ToResourceNotFoundException() {
        server.enqueue(new MockResponse().setResponseCode(404).setBody("{\"message\":\"not found\"}"));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).paymentServiceId("svc");
        var svc = client.getPaymentService();
        assertThatThrownBy(() -> svc.getTransactionInfo("x")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void maps422ToValidationFailedException() {
        String body = "{\"message\":\"Validation failed\",\"errors\":{\"field\":[\"err\"]}}";
        server.enqueue(new MockResponse().setResponseCode(422).setBody(body));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).paymentServiceId("svc");
        var svc = client.getPaymentService();
        assertThatThrownBy(() -> svc.getTransactionInfo("x")).isInstanceOf(ValidationFailedException.class)
                .hasMessageContaining("Validation failed");
    }

    @Test
    void mapsOtherErrorsToSimPayException() {
        server.enqueue(new MockResponse().setResponseCode(500).setBody("{\"message\":\"boom\"}"));
        SimPayClient client = new SimPayClient("t").baseUrl(server.url("/").toString()).paymentServiceId("svc");
        var svc = client.getPaymentService();
        assertThatThrownBy(() -> svc.getTransactionInfo("x")).isInstanceOf(SimPayException.class)
                .hasMessageContaining("unexpected api error");
    }
}
