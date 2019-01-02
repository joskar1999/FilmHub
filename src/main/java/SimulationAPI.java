package main.java;

import main.java.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SimulationAPI {

    private static Service service = new Service();

    public SimulationAPI() {
        service.initialize();
        service.start();
    }

    public static Subscription getSubscription() {
        return service.getSubscription();
    }

    public static TimeUtils getTimeUtils() {
        return service.getTimeUtils();
    }

    public static void setSubscriptionPrice(BigDecimal basic, BigDecimal family, BigDecimal premium) {
        service.setSubscriptionPrice(basic, family, premium);
    }

    public static List<Product> getProducts() {
        return service.getProducts();
    }

    public static List<User> getUsers() {
        return service.getUsers();
    }

    public static List<Distributor> getDistributors() {
        return service.getDistributors();
    }

    public static SimulationSettings getSimulationSettings() {
        return service.getSimulationSettings();
    }

    public static Map<String, Integer> getGeneralWatchesAmountMap() {
        return service.getGeneralWatchesAmountMap();
    }

    public static int getMovieAmount() {
        return service.getMovieAmount();
    }

    public static List<Product> getMostPopular(int amount) {
        return service.getMostPopular(amount);
    }

    public static void createNewUser() {
        service.createNewUser();
    }

    public static void createNewDistributor() {
        service.createNewDistributor();
    }

    public static Product searchForProduct(String title) {
        return service.searchForProduct(title);
    }

    public static void removeProduct(String title) {
        service.removeProduct(title);
    }

    public static void removeUser(String email) {
        service.removeUser(email);
    }

    public static void removeDistributor(String name) {
        service.removeDistributor(name);
    }

    public static void addDiscount(String productTitle, Discount discount, int type) {
        service.addDiscount(productTitle, discount, type);
    }

    public static void setNewPrice(String title, BigDecimal price) {
        service.setNewPrice(title, price);
    }

    public static void serialize() {
        service.serialize();
    }

    public static void deserialize() {
        service.deserialize();
    }

    public void initialize() {
        service.initialize();
    }

    public void start() {
        service.start();
    }
}
