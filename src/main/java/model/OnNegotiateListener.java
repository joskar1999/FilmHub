package main.java.model;

@FunctionalInterface
public interface OnNegotiateListener {

    /**
     * Negotiation between Service and Distributor
     * Invoked by Distributor when decide to renegotiate Contract
     *
     * @param percentages   value proposed by Distributor
     * @param pricePerWatch value per watch proposed by distributor
     */
    void onNegotiate(int percentages, int pricePerWatch);
}
