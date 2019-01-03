package main.java.model;

@FunctionalInterface
public interface OnSimulationEndListener {

    /**
     * Method should be called by service
     * when simulation will end
     */
    void onSimulationEnd();
}
