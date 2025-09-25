package pl.zaliczto.simpay.responses.payment.transactioninfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerFullDataResponse {
    private String name;
    private String surname;
    private String street;
    private String building;
    private String flat;
    private String city;
    private String region;
    @JsonProperty("postalCode")
    private String postalCode;
    private String country;
    private String company;
}

