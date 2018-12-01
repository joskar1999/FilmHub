package main.java.model;

@FunctionalInterface
public interface OnPaymentListener {

    /**
     * Invoke when payment period occurs
     *
     * @param product product title which is bought by user
     * @param userId  user identifier in user base
     */
    void onPayment(String product, int userId);
}
