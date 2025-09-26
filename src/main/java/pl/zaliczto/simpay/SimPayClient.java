package pl.zaliczto.simpay;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import pl.zaliczto.simpay.services.directbilling.DirectBillingService;
import pl.zaliczto.simpay.services.payment.PaymentService;
import pl.zaliczto.simpay.services.sms.SmsService;


/**
 * The type Sim pay client.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class SimPayClient {

    private String bearerToken;
    private final String version = "1.0.0";
    private String baseUrl = "https://api.simpay.pl/";
    private String paymentServiceId;
    private String directBillingServiceId;
    private String smsServiceId;
    private String paymentHash;
    private String directBillingHash;
    private final String platform = "JAVA-UNOFFICIAL";

    private PaymentService paymentService;
    private DirectBillingService directBillingService;
    private SmsService smsService;


    /**
     * Instantiates a new Sim pay client.
     *
     * @param bearerToken the bearer token
     */
    public SimPayClient(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    /**
     * Instantiates a new Sim pay client.
     *
     * @param bearerToken the bearer token from the simpay.pl panel
     * @param baseUrl     the base url (change if you want to use the mock api server)
     */
    public SimPayClient(String bearerToken, String baseUrl) {
        this.bearerToken = bearerToken;
        this.baseUrl = baseUrl;
    }

    /**
     * Gets payment service.
     *
     * @return the payment service
     */
    public PaymentService getPaymentService() {
        if (paymentService == null) {
            paymentService = new PaymentService(this);
        }
        return paymentService;
    }

    /**
     * Gets direct billing service.
     *
     * @return the direct billing service
     */
    public DirectBillingService getDirectBillingService() {
        if (directBillingService == null) {
            directBillingService = new DirectBillingService(this);
        }
        return directBillingService;
    }

    /**
     * Gets sms service.
     *
     * @return the sms service
     */
    public SmsService getSmsService() {
        if (smsService == null) {
            smsService = new SmsService(this);
        }
        return smsService;
    }

}
