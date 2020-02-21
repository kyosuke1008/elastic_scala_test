package model;

import java.math.BigDecimal;

public enum Instruments {
    USD_JPY(new BigDecimal(0.20)),
    EUR_JPY(new BigDecimal(0.20)),
    EUR_USD(new BigDecimal(0.0020));

    private final BigDecimal diff;

    Instruments(BigDecimal diff) {
        this.diff = diff;
    }

    public BigDecimal getDiff() {
        return this.diff;
    }
}
