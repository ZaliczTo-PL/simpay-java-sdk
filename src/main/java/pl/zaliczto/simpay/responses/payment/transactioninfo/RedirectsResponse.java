package pl.zaliczto.simpay.responses.payment.transactioninfo;

import lombok.Data;

@Data
public class RedirectsResponse {
    private String success;
    private String failure;
}

