package pl.zaliczto.simpay.services.core;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.AuthorizationException;
import pl.zaliczto.simpay.exceptions.ResourceNotFoundException;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.exceptions.ValidationFailedException;
import pl.zaliczto.simpay.util.Jsons;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

/**
 * The base service class for all services.
 */
public abstract class HttpService {
    /**
     * The Client.
     */
    protected final SimPayClient client;
    private static final OkHttpClient HTTP = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Instantiates a new Http service.
     *
     * @param client the client
     */
    protected HttpService(SimPayClient client) {
        this.client = client;
    }

    /**
     *  Preforms a raw request to the simpay api and returns the response envelope.
     *
     * @param method  the method
     * @param path    the path
     * @param payload the payload
     * @return the response envelope
     * @throws SimPayException the sim pay exception
     */
    protected ResponseEnvelope requestRaw(String method, String path, Object payload) throws SimPayException {
        String base = client.baseUrl();
        if (!base.endsWith("/")) {
            base += "/";
        }
        String url = base + path;

        RequestBody body = null;
        if (payload != null) {
            try {
                String json = Jsons.MAPPER.writeValueAsString(payload);
                body = RequestBody.create(json, JSON);
            } catch (Exception e) {
                throw new SimPayException("Failed to serialize JSON request", e);
            }
        }

        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + client.bearerToken())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("X-SIM-VERSION", client.version())
                .header("X-SIM-PLATFORM", client.platform());

        switch (method.toUpperCase()) {
            case "GET" -> builder.get();
            case "POST" -> builder.post(body != null ? body : RequestBody.create(new byte[0], null));
            case "PUT" -> builder.put(body != null ? body : RequestBody.create(new byte[0], null));
            case "DELETE" -> builder.delete(body);
            default -> throw new SimPayException("Unsupported HTTP method: " + method);
        }

        try (Response response = HTTP.newCall(builder.build()).execute()) {
            int code = response.code();
            String raw = response.body() != null ? response.body().string() : "";
            JsonNode root = null;
            if (raw != null && !raw.isEmpty()) {
                try { root = Jsons.MAPPER.readTree(raw); } catch (Exception ignored) {}
            }
            return new ResponseEnvelope(code, raw, root);
        } catch (IOException e) {
            throw new SimPayException("HTTP connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Preforms a request to the simpay api and returns the response envelope.
     *
     * @param method  the method
     * @param path    the path
     * @param payload the payload
     * @return the response envelope
     * @throws SimPayException the sim pay exception
     */
    protected ResponseEnvelope request(String method, String path, Object payload)
            throws SimPayException {
        ResponseEnvelope env = requestRaw(method, path, payload);
        int code = env.status();
        JsonNode root = env.json();
        String raw = env.body();

        if (code >= 200 && code < 300) {
            return env;
        }

        String message = root != null && root.has("message") ? root.path("message").asText() : raw;
        String errorCode = root != null && root.has("errorCode") ? root.path("errorCode").asText() : null;

        if (code == 401 || code == 403) {
            throw new AuthorizationException(message != null ? message : "Unauthorized", errorCode);
        }
        if (code == 404) {
            throw new ResourceNotFoundException(message != null ? message : "Resource not found");
        }
        if (code == 422) {
            Map<String, Object> errors = null;
            if (root != null && root.has("errors")) {
                errors = Jsons.MAPPER.convertValue(root.get("errors"), new TypeReference<Map<String, Object>>() {});
            }
            throw new ValidationFailedException(errors, message != null ? message : "Validation failed");
        }

        throw new SimPayException("unexpected api error. " + code + ": " + (message != null ? message : raw), errorCode);
    }

    /**
     * Payment service id string.
     *
     * @return the string
     */
    protected String paymentServiceId() { return client.paymentServiceId(); }

    /**
     * Direct billing service id string.
     *
     * @return the string
     */
    protected String directBillingServiceId() { return client.directBillingServiceId(); }

    /**
     * Sms service id string.
     *
     * @return the string
     */
    protected String smsServiceId() { return client.smsServiceId(); }

    /**
     * Payment hash string.
     *
     * @return the string
     */
    protected String paymentHash() { return client.paymentHash(); }

    /**
     * Direct billing hash string.
     *
     * @return the string
     */
    protected String directBillingHash() { return client.directBillingHash(); }


    /**
     * The type Response envelope.
     */
    protected record ResponseEnvelope(int status, String body, JsonNode json) {}
}
