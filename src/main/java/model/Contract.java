package main.java.model;

public class Contract {

    private long startDate;
    private long duration;
    private int percentages;

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getPercentages() {
        return percentages;
    }

    public void setPercentages(int percentages) {
        this.percentages = percentages;
    }
}
