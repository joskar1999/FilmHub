package main.java.model;

public class SimulationSettings {

    private int basicMultiplier;
    private int multiplier;
    public static final int STARTING_DISTRIBUTORS_AMOUNT = 6;
    public static final int STARTING_USERS_AMOUNT = 20;
    public static final int STARTING_PERCENTAGES = 25;
    public static final int STARTING_PRICE_PER_WATCH = 5;
    public static final int WATCH_TO_BUY_RATIO = 3;
    public static final double PRODUCT_PRICE_LOWER_BOUND = 10.0;
    public static final double PRODUCT_PRICE_UPPER_BOUND = 25.0;

    /**
     * Basically 1s in simulation is equiv to 1 hour in real time,
     * if you want to make it quicker, just set multiplier
     */
    public SimulationSettings() {
        this.basicMultiplier = 3600;
    }

    public int getBasicMultiplier() {
        return basicMultiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
