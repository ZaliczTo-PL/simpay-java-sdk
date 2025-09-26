package pl.zaliczto.simpay.enums.directbilling;

import lombok.Getter;

/**
 * The enum Mobile carrier.
 */
@Getter
public enum MobileCarrier {
    /**
     * Orange mobile carrier.
     */
    Orange("orange"),
    /**
     * Play mobile carrier.
     */
    Play("play"),
    /**
     * T mobile mobile carrier.
     */
    TMobile("t-mobile"),
    /**
     * Plus mobile carrier.
     */
    Plus("plus"),
    /**
     * Netia mobile carrier.
     */
    Netia("netia");

    private final String value;
    MobileCarrier(String value) { this.value = value; }


    /**
     * From mobile carrier.
     *
     * @param v the value
     * @return the mobile carrier
     */
    public static MobileCarrier from(String v) {
        for (MobileCarrier c : values()) if (c.value.equalsIgnoreCase(v)) return c;
        throw new IllegalArgumentException("Unknown carrier: " + v);
    }
}

