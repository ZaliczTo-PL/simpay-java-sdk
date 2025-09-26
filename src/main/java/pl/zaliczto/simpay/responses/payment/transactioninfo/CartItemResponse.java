package pl.zaliczto.simpay.responses.payment.transactioninfo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * The type Cart item response.
 */
@Data
public class CartItemResponse {
    private String name;
    private int quantity;
    private BigDecimal price;
    private String producer;
    private String category;
    private String code;
}
