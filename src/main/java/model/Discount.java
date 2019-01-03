package main.java.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Discount implements Serializable {

    private long startTime;
    private long endTime;
    private BigDecimal percentages;

    public Discount() {
        this.startTime = 0;
        this.endTime = 0;
        this.percentages = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPercentages() {
        return percentages;
    }

    public void setPercentages(BigDecimal percentages) {
        this.percentages = percentages;
    }
}
