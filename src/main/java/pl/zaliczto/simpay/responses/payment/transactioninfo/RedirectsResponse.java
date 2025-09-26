package pl.zaliczto.simpay.responses.payment.transactioninfo;

import lombok.Data;

/**
 * The type Redirects response.
 */
@Data
public class RedirectsResponse {
    private String success;
    private String failure;
}

