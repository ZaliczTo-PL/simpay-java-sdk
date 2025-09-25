package pl.zaliczto.simpay.enums.payment;

public enum BlikLevel0TicketType {
    T6("T6");

    private final String value;
    BlikLevel0TicketType(String value) { this.value = value; }
    public String getValue() { return value; }
}

