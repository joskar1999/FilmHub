package main.java.model;

public interface OnDistributorsSetChangeListener {

    /**
     * Method should be called when new distributor is created
     * and view should be updated
     *
     * @param distributor created distributor
     */
    void onDistributorCreated(Distributor distributor);

    /**
     * Method should be called when user is deleted
     * from service and view should be updated
     *
     * @param distributor removed distributor
     */
    void onDistributorRemoved(Distributor distributor);
}
