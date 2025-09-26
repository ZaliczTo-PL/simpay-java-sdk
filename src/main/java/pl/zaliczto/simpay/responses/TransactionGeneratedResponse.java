package pl.zaliczto.simpay.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Transaction generated response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionGeneratedResponse {
    private String url;
    private String transactionId;
}

