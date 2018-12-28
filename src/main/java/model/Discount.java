package main.java.model;

import java.math.BigDecimal;

public class Discount {

    private long startTime;
    private long endTime;
    private BigDecimal percentages;

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
