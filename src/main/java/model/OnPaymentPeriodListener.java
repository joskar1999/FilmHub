package main.java.model;

@FunctionalInterface
public interface OnPaymentPeriodListener {

    /**
     * Invoke when payment period occurs
     */
    void onPaymentPeriod();
}
