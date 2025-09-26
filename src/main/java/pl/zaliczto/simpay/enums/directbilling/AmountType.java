package pl.zaliczto.simpay.enums.directbilling;

import lombok.Getter;

/**
 * The enum Amount type.
 */
@Getter
public enum AmountType {
    /**
     * Required amount type.
     */
    Required("required"),
    /**
     * Net amount type.
     */
    Net("net"),
    /**
     * Gross amount type.
     */
    Gross("gross");

    private final String value;
    AmountType(String value) { this.value = value; }

    /**
     * From amount type.
     *
     * @param v the value
     * @return the amount type
     */
    public static AmountType from(String v) {
        for (AmountType t : values()) if (t.value.equalsIgnoreCase(v)) return t;
        throw new IllegalArgumentException("Unknown amount type: " + v);
    }
}

