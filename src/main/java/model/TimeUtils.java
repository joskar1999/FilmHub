package main.java.model;

import main.java.SimulationAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils implements Runnable {

    private long startTimestamp;
    private long currentTimestamp;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat dayDateFormat;
    private OnPaymentPeriodListener onPaymentPeriodListener;
    private boolean testPaymentPeriod;
    private boolean shouldStop = false;

    public TimeUtils() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dayDateFormat = new SimpleDateFormat("dd");
        testPaymentPeriod = false;
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
        diff *= SimulationAPI.getSimulationSettings().getMultiplier();
        diff *= SimulationAPI.getSimulationSettings().getBasicMultiplier();
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

    /**
     * Checking if payment period occurs,
     * payment period is treated as 1st every month
     *
     * @return true if occurs, false otherwise
     */
    private boolean checkForPaymentPeriod() {
        Date date = new Date(currentTimestamp * 1000L);
        String formatted = dayDateFormat.format(date);
        if (formatted.equals("01") && testPaymentPeriod == false) {
            testPaymentPeriod = true;
            return true;
        } else if (!formatted.equals("01")) {
            testPaymentPeriod = false;
        }
        return false;
    }

    public void kill() {
        this.shouldStop = true;
    }

    public void addOnPaymentPeriodListener(OnPaymentPeriodListener onPaymentPeriodListener) {
        this.onPaymentPeriodListener = onPaymentPeriodListener;
    }

    @Override
    public void run() {
        startTimestamp = System.currentTimeMillis() / 1000L;
        while (!shouldStop) {
            calculateCurrentTimestamp();
            if (checkForPaymentPeriod()) {
                onPaymentPeriodListener.onPaymentPeriod();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
