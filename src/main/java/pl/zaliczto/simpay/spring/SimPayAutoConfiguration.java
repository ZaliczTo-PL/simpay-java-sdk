package pl.zaliczto.simpay.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.zaliczto.simpay.SimPayClient;

@Configuration
@ConditionalOnClass(SimPayClient.class)
@EnableConfigurationProperties(SimPayProperties.class)
public class SimPayAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SimPayClient simPayClient(SimPayProperties props) {
        SimPayClient client = props.getBaseUrl() != null
                ? new SimPayClient(props.getBearerToken(), props.getBaseUrl())
                : new SimPayClient(props.getBearerToken());
        if (props.getPaymentServiceId() != null) client.paymentServiceId(props.getPaymentServiceId());
        if (props.getDirectBillingServiceId() != null) client.directBillingServiceId(props.getDirectBillingServiceId());
        if (props.getSmsServiceId() != null) client.smsServiceId(props.getSmsServiceId());
        if (props.getPaymentHash() != null) client.paymentHash(props.getPaymentHash());
        if (props.getDirectBillingHash() != null) client.directBillingHash(props.getDirectBillingHash());
        return client;
    }
}
