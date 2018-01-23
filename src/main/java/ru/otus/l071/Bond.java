package ru.otus.l071;

public enum Bond {
    FIVE_THOUSANDS(5000),
    ONE_THOUSAND(1000),
    FIVE_HUNDREDS(500),
    ONE_HUNDRED(100);

    private final int denomination;

    Bond(int value) {
        denomination = value;
    }

    public int value() {
        return denomination;
    }

    @Override
    public String toString() {
        return "Bond{"
             + " denomination = " + denomination
             + " }";
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
