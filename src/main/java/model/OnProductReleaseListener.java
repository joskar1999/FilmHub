package main.java.model;

@FunctionalInterface
public interface OnProductReleaseListener {

    /**
     * Invoke when distributor release a new Product
     *
     * @param product Product to be released
     * @param id      Distributor id
     */
    void onProductRelease(Product product, int id);
}
