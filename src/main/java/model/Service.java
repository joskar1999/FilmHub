package main.java.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Service {

    private static List<User> users = new ArrayList<>();
    private static List<Distributor> distributors = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static Map<String, Integer> productDistributorMapping = new HashMap<>();
    private static SimulationSettings simulationSettings = new SimulationSettings();
    private static TimeUtils timeUtils = new TimeUtils();
    private static Subscription subscription = new Subscription();
    private static BigDecimal serviceBankAccount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
    private static int movieAmount = 0;
    private static int usersAmount = 0;
    private static int distributorsAmount = 0;
    private static Semaphore semaphore;
    private static Random random = new Random();
    private static OnDatasetChangeListener onDatasetChangeListener;
    public static final int LIVE_DISCOUNT = 1;
    public static final int MOVIE_DISCOUNT = 2;

    public Service() {
        simulationSettings.setMultiplier(10);
        semaphore = new Semaphore(1);
    }

    public static Subscription getSubscription() {
        return subscription;
    }

    public static TimeUtils getTimeUtils() {
        return timeUtils;
    }

    public static void setSubscriptionPrice(BigDecimal basic, BigDecimal family, BigDecimal premium) {
        if (basic != null) {
            subscription.updateBasicPrice(basic);
        }
        if (family != null) {
            subscription.updateFamilyPrice(family);
        }
        if (premium != null) {
            subscription.updatePremiumPrice(premium);
        }
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static SimulationSettings getSimulationSettings() {
        return simulationSettings;
    }

    public static int getMovieAmount() {
        return movieAmount;
    }

    public static void addOnDatasetChangeListener(OnDatasetChangeListener onDatasetChangeListener) {
        Service.onDatasetChangeListener = onDatasetChangeListener;
    }

    /**
     * Sending money to distributor, increasing value of
     * Service 'bank account'
     */
    private static synchronized void executePayment(String product, int userId) {
        //TODO implement distributor salary
        for (Product p : products) {
            if (p.getTitle().equals(product)) {
                serviceBankAccount = serviceBankAccount.add(p.getPrice());
//                System.out.println(serviceBankAccount);
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
//        System.out.println(serviceBankAccount);
    }

    /**
     * Negotiation between Service and Distributor
     *
     * @param distributor Distributor which negotiates
     * @param p           value wanted by Distributor
     */
    private static void negotiate(Distributor distributor, int p) {
        int sp = random.nextInt(40);
        Contract c = new Contract();
        if (sp > p) {
            c.setPercentages(p);
        } else {
            int m = (p + sp) / 2;
            c.setPercentages(m);
        }
        distributor.setContract(c);
    }

    /**
     * Getting most popular products from Service, ordered by rating
     *
     * @param amount amount of products that method will return
     * @return products list ordered by rating
     */
    public static List<Product> getMostPopular(int amount) {
        List<Product> p = new ArrayList<>();
        List<Product> popular = new ArrayList<>();
        p = getProducts();
        Collections.sort(p);
        for (int i = 0; i < amount; i++) {
            try {
                popular.add(p.get(i));
            } catch (Exception e) {
                //
            }
        }
        return (ArrayList<Product>) popular;
    }

    public static void createNewUser() {
        User user = new User(usersAmount++);
        user.addOnPaymentListener((p, id) -> {
            executePayment(p, id);
        });
        users.add(user);
        runThread(user);
    }

    public static void createNewDistributor() {
        Distributor distributor = new Distributor(distributorsAmount++, semaphore);

        distributor.addOnProductReleaseListener((p, id) -> {
            products.add(p);
            productDistributorMapping.put(p.getTitle(), id);
            System.out.println(p.getTitle() + ", " + String.valueOf(id));
            movieAmount++;

            if (movieAmount >= 7) { //when id is lower that 7, MainController have not been created yet, so listener is null
                onDatasetChangeListener.notifyDatasetChanged(p);
            }
        });

        distributor.addOnNegotiateListener((p) -> {
            negotiate(distributor, p);
        });

        distributors.add(distributor);
        runThread(distributor);
    }

    private static void runThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Searching in products base for requested product
     *
     * @param title Searched product title
     * @return Product, if is in products base, else null
     */
    public static Product searchForProduct(String title) {
        for (Product p : products) {
            if (p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }

    public static void removeProduct(String title) {
        products.removeIf(p -> p.getTitle().equals(title));
        //Product will be removed only from 'products'
        //because it may cause NullPointerException
        //in User object if it will be removed from other List
    }

    public static void addDiscount(String productTitle, Discount discount, int type) {
        if (type == LIVE_DISCOUNT) {
            for (Product p : products) {
                if (p instanceof Live && p.getTitle().equals(productTitle)) {
                    ((Live) p).setDiscount(discount);
                }
            }
        } else if (type == MOVIE_DISCOUNT) {
            for (Product p : products) {
                if (p instanceof Movie && p.getTitle().equals(productTitle)) {
                    ((Movie) p).setDiscount(discount);
                }
            }
        }
    }

    /**
     * Initializing lists with some content -
     * creating users and products
     */
    public void initialize() {
        timeUtils.addOnPaymentPeriodListener(() -> {
//            System.out.println("Payment period");
            collectSubscriptionFee();
        });

        for (int i = 0; i < 20; i++) {
            createNewUser();
        }

        for (int i = 0; i < 6; i++) {
            createNewDistributor();
        }
    }

    /**
     * Running threads
     */
    public void start() {
        Thread timeUtilsThread = new Thread(timeUtils);
        timeUtilsThread.setDaemon(true);
        timeUtilsThread.start();
    }

    public static void main(String[] args) {
        Service service = new Service();
        service.initialize();
        service.start();
    }
}
