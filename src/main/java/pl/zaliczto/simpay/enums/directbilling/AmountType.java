package pl.zaliczto.simpay.enums.directbilling;

public enum AmountType {
    Required("required"),
    Net("net"),
    Gross("gross");

    private final String value;
    AmountType(String value) { this.value = value; }
    public String getValue() { return value; }

    public static AmountType from(String v) {
        for (AmountType t : values()) if (t.value.equalsIgnoreCase(v)) return t;
        throw new IllegalArgumentException("Unknown amount type: " + v);
    }
}

