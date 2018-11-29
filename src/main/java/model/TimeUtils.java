package main.java.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils implements Runnable {

    private long startTimestamp;
    private long currentTimestamp;
    private SimpleDateFormat simpleDateFormat;

    public TimeUtils() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getCurrentTimestamp() {
        return currentTimestamp;
    }

    private void calculateCurrentTimestamp() {
        long diff = (System.currentTimeMillis() / 1000L) - startTimestamp;
        diff *= Service.getSimulationSettings().getMultiplier();
        diff *= Service.getSimulationSettings().getBasicMultiplier();
        this.currentTimestamp = diff + startTimestamp;
    }

    /**
     * Calculating current time in simulation
     *
     * @return current time in simulation in readable format
     */
    public String getCurrentTime() {
        Date date = new Date(currentTimestamp * 1000L);
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }

    @Override
    public void run() {
        startTimestamp = System.currentTimeMillis() / 1000L;
        while (true) {
            calculateCurrentTimestamp();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
