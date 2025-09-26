package pl.zaliczto.simpay.services.payment;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import lombok.experimental.Accessors;
import pl.zaliczto.simpay.SimPayClient;
import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.model.*;
import pl.zaliczto.simpay.responses.TransactionGeneratedResponse;
import pl.zaliczto.simpay.services.core.HttpService;

import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

/**
 * The type Generate transaction.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
public class GenerateTransaction extends HttpService {

    private BigDecimal amount;
    private String currency = "PLN";
    private String description;
    private String control;
    private CustomerData customer;
    private AntiFraudData antiFraud;
    private CustomerFullData billing;
    private CustomerFullData shipping;
    private List<CartItem> cart;
    private ReturnData returns;
    private String directChannel;
    private List<String> channels;
    private ChannelType channelTypes;
    private String referer;

    /**
     * Instantiates a new Generate transaction.
     *
     * @param client the client
     */
    public GenerateTransaction(SimPayClient client) {
        super(client);
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     * @return the amount
     */
    public GenerateTransaction setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
        return this;
    }

    /**
     * Generates the transaction.
     *
     * @return the transaction generated response
     * @throws SimPayException the sim pay exception
     */
    public TransactionGeneratedResponse generate() throws SimPayException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SimPayException("Amount must be set and greater than zero before generating a transaction");
        }
        String serviceId = client.paymentServiceId();
        if (serviceId == null || serviceId.isBlank()) {
            throw new SimPayException("Missing payment service identifier (paymentServiceId)");
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("amount", amount);
        payload.put("currency", currency);
        if (description != null) payload.put("description", description);
        if (control != null) payload.put("control", control);
        if (customer != null) payload.put("customer", customer);
        if (antiFraud != null) payload.put("antifraud", antiFraud);
        if (billing != null) payload.put("billing", billing);
        if (shipping != null) payload.put("shipping", shipping);
        if (cart != null && !cart.isEmpty()) {
            payload.put("cart", cart);
        }
        if (returns != null) payload.put("returns", returns);
        if (directChannel != null) payload.put("directChannel", directChannel);
        if (channels != null && !channels.isEmpty()) payload.put("channels", channels);
        if (channelTypes != null) payload.put("channelTypes", channelTypes);
        if (referer != null) payload.put("referer", referer);

        ResponseEnvelope resp = request("POST", String.format("payment/%s/transactions", serviceId), payload);
        JsonNode data = resp.json() != null ? resp.json().path("data") : null;
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

    /**
     * Sets the  cart items.
     *
     * @param items the items
     * @return the generate transaction
     */
    public GenerateTransaction cartItems(List<CartItem> items) {
        this.cart = items;
        return this;
    }

    /**
     * Add cart item
     *
     * @param item the item
     * @return the generate transaction
     */
    public GenerateTransaction addCartItem(CartItem item) {
        if (this.cart == null) this.cart = new ArrayList<>();
        this.cart.add(item);
        return this;
    }

    /**
     * Sets the available channels.
     *
     * @param channels the channels
     * @return the generate transaction
     */
    public GenerateTransaction channelsList(List<String> channels) {
        this.channels = channels;
        return this;
    }
}
