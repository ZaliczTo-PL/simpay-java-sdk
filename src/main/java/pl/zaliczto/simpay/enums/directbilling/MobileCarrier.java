package pl.zaliczto.simpay.enums.directbilling;

public enum MobileCarrier {
    Orange("orange"),
    Play("play"),
    TMobile("t-mobile"),
    Plus("plus"),
    Netia("netia");

    private final String value;
    MobileCarrier(String value) { this.value = value; }
    public String getValue() { return value; }

    public static MobileCarrier from(String v) {
        for (MobileCarrier c : values()) if (c.value.equalsIgnoreCase(v)) return c;
        throw new IllegalArgumentException("Unknown carrier: " + v);
    }
}

