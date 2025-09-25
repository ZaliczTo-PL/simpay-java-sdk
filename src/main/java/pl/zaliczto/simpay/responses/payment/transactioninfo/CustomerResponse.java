package pl.zaliczto.simpay.responses.payment.transactioninfo;

import lombok.Data;

@Data
public class CustomerResponse {
    private String name;
    private String email;
    private String ip;
    private String country;
}

