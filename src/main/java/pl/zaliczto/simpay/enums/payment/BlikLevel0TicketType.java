package pl.zaliczto.simpay.enums.payment;

import lombok.Getter;

/**
 * The enum Blik level 0 ticket type.
 */
@Getter
public enum BlikLevel0TicketType {
    /**
     * T 6 blik level 0 ticket type.
     */
    T6("T6");

    private final String value;
    BlikLevel0TicketType(String value) { this.value = value; }


}

