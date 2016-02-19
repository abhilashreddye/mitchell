package com.mitchell.claims.utilities;

public enum SQLResult {

    SUCCESS("Success"),
    FAIL("Fail");
    
    private final String value;

    SQLResult(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SQLResult fromValue(String v) {
        for (SQLResult c: SQLResult.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
