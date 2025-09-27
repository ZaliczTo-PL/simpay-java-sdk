package pl.zaliczto.simpay;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import pl.zaliczto.simpay.exceptions.SimPayException;
import pl.zaliczto.simpay.model.AntiFraudData;
import pl.zaliczto.simpay.model.CustomerData;
import pl.zaliczto.simpay.responses.TransactionGeneratedResponse;
import pl.zaliczto.simpay.services.payment.PaymentService;
import pl.zaliczto.simpay.services.directbilling.DirectBillingService;
import pl.zaliczto.simpay.services.sms.SmsService;

import java.util.List;

class SimPayClientTest {
    @Test
    public void makePayment() throws SimPayException {
        SimPayClient client = new SimPayClient("token").paymentServiceId("serviceid");

        TransactionGeneratedResponse transRsp = client.getPaymentService().generate()
                .setAmount(1.0)
                .setCurrency("PLN")
                .setDescription("testowa platnosc")
                .channelsList(List.of("blik","mbank" ,"pko_bp"))
                .setAntiFraud(AntiFraudData.builder().userAgent("JUnit Test").build())
                .setCustomer(new CustomerData("Stanisław", "me@nightowl.dev", "127.0.0.0", "PL"))
                .generate();

        System.out.println("Payment URL: " + transRsp.getUrl());
    }
    @Test
    void fluentSettersAndServiceCaching() {
        SimPayClient client = new SimPayClient("token").baseUrl("http://example").paymentServiceId("p1");
        assertThat(client.bearerToken()).isEqualTo("token");
        assertThat(client.baseUrl()).isEqualTo("http://example");
        assertThat(client.paymentServiceId()).isEqualTo("p1");

        PaymentService p1 = client.getPaymentService();
        PaymentService p2 = client.getPaymentService();
        assertThat(p1).isSameAs(p2);

        DirectBillingService d1 = client.getDirectBillingService();
        DirectBillingService d2 = client.getDirectBillingService();
        assertThat(d1).isSameAs(d2);

        SmsService s1 = client.getSmsService();
        SmsService s2 = client.getSmsService();
        assertThat(s1).isSameAs(s2);
    }
}
