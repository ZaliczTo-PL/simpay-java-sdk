package pl.zaliczto.simpay.responses.payment.transactioninfo;

import lombok.Data;

/**
 * The type Customer response.
 */
@Data
public class CustomerResponse {
    private String name;
    private String email;
    private String ip;
    private String country;
}

