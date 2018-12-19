package main.java.model;

@FunctionalInterface
public interface OnDatasetChangeListener {

    /**
     * This method should be used to communication
     * between Service and Controller, to notify that
     * dataset changed and view should be updated
     */
    void notifyDatasetChanged();
}
