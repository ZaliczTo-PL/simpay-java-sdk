package pl.zaliczto.simpay.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "simpay.sdk")
public class SimPayProperties {
    private String bearerToken;
    private String baseUrl = "https://api.simpay.pl/";
    private String paymentServiceId;
    private String directBillingServiceId;
    private String smsServiceId;
    private String paymentHash;
    private String directBillingHash;

}

