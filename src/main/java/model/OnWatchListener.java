package main.java.model;

@FunctionalInterface
public interface OnWatchListener {

    /**
     * Method should be invoke by user when it watch
     * single product and service should be informed about it
     *
     * @param title product title
     */
    void onWatch(String title);
}
