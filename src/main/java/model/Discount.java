package main.java.model;

public class Discount {

    private long startTime;
    private long endTime;
    private int percentages;

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

    public int getPercentages() {
        return percentages;
    }

    public void setPercentages(int percentages) {
        this.percentages = percentages;
    }
}
