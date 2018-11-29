package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<User> users = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static SimulationSettings simulationSettings = new SimulationSettings();
    private long startingUnixTimeStamp;

    public Service() {
        simulationSettings.setMultiplier(10);
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static SimulationSettings getSimulationSettings() {
        return simulationSettings;
    }

    /**
     * Getting current unix timestamp
     *
     * @return timestamp in seconds (unix epoch)
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * Initializing lists with some content -
     * creating users and products
     */
    public void initialize() {
        for (int i = 0; i < 20; i++) {
            User user = new User(i);
            users.add(user);
        }

        for (int i = 0; i < 6; i++) {
            Product product = new Movie(i);
            products.add(product);
        }
    }

    /**
     * Running threads
     */
    public void start() {
        startingUnixTimeStamp = getCurrentTimestamp();
        Runnable runnable = users.get(0);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void main(String[] args) {
        Service service = new Service();
        service.initialize();
        service.start();
    }
}
