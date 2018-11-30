package main.java.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<User> users = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static SimulationSettings simulationSettings = new SimulationSettings();
    private static TimeUtils timeUtils = new TimeUtils();
    private static Subscription subscription = new Subscription();
    private BigDecimal serviceBankAccount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);

    public Service() {
        simulationSettings.setMultiplier(10);
    }

    public static Subscription getSubscription() {
        return subscription;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static SimulationSettings getSimulationSettings() {
        return simulationSettings;
    }

    /**
     * Sending money to distributor, increasing value of
     * Service 'bank account'
     */
    private synchronized void executePayment(String product, int userId) {
        //TODO implement distributor salary
        for (Product p : products) {
            if (p.getTitle().equals(product)) {
                serviceBankAccount = serviceBankAccount.add(p.getPrice());
                System.out.println(serviceBankAccount);
            }
        }
    }

    /**
     * Getting subscription fee from users,
     * randomizing new subscription type
     */
    private synchronized void collectSubscriptionFee() {
        for (User u : users) {
            BigDecimal toPay = u.getSubscriptionPayment();
            BigDecimal reset = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
            serviceBankAccount = serviceBankAccount.add(toPay);
            u.randomizeSubscription();
        }
        System.out.println(serviceBankAccount);
    }

    /**
     * Initializing lists with some content -
     * creating users and products
     */
    public void initialize() {
        timeUtils.addOnPaymentPeriodListener(() -> {
            System.out.println("Payment period");
            collectSubscriptionFee();
        });

        for (int i = 0; i < 20; i++) {
            User user = new User(i);
            user.addOnPaymentListener((p, id) -> {
                executePayment(p, id);
            });
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
        Thread timeUtilsThread = new Thread(timeUtils);
        timeUtilsThread.start();

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(users.get(i));
            t.start();
        }
    }

    public static void main(String[] args) {
        Service service = new Service();
        service.initialize();
        service.start();
    }
}
