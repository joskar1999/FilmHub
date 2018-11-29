package main.java.model;

public class SimulationSettings {

    private int basicMultiplier;
    private int multiplier;

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
