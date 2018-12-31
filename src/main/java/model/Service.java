package main.java.model;

import main.java.controller.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Semaphore;

import static main.java.model.SimulationSettings.STARTING_DISTRIBUTORS_AMOUNT;
import static main.java.model.SimulationSettings.STARTING_USERS_AMOUNT;

public class Service {

    private static List<User> users = new ArrayList<>();
    private static List<Distributor> distributors = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static Map<String, Integer> productDistributorMapping = new HashMap<>();
    private static Map<String, Integer> monthlyWatchesAmountMap = new HashMap<>();
    private static Map<String, Integer> generalWatchesAmountMap = new HashMap<>();
    private static Map<String, ArrayList<Integer>> productsWatchesAmountMap = new HashMap<>();
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
    private static OnUsersSetChangeListener onUsersSetChangeListener;
    private static OnDistributorsSetChangeListener onDistributorsSetChangeListener;
    public static boolean isUsersViewCreated = false;
    public static boolean isDistributorsViewCreated = false;
    public static final int NO_DISCOUNT = 0;
    public static final int LIVE_DISCOUNT = 1;
    public static final int MOVIE_DISCOUNT = 2;
    private static int negativeIncomes = 0;

    public Service() {
        simulationSettings.setMultiplier(20);
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

    public static List<User> getUsers() {
        return users;
    }

    public static List<Distributor> getDistributors() {
        return distributors;
    }

    public static SimulationSettings getSimulationSettings() {
        return simulationSettings;
    }

    public static Map<String, Integer> getGeneralWatchesAmountMap() {
        return generalWatchesAmountMap;
    }

    public static int getMovieAmount() {
        return movieAmount;
    }

    public static void addOnDatasetChangeListener(OnDatasetChangeListener onDatasetChangeListener) {
        Service.onDatasetChangeListener = onDatasetChangeListener;
    }

    public static void addOnUsersSetChangeListener(OnUsersSetChangeListener onUsersSetChangeListener) {
        Service.onUsersSetChangeListener = onUsersSetChangeListener;
    }

    public static void addOnDistributorsSetChangeListener(OnDistributorsSetChangeListener onDistributorsSetChangeListener) {
        Service.onDistributorsSetChangeListener = onDistributorsSetChangeListener;
    }

    /**
     * Sending money to distributor, increasing value of
     * Service 'bank account'; Calculating discount
     */
    private static synchronized void executePayment(String product, int userId, final int discountType) {
        for (Product p : products) {
            if (p.getTitle().equals(product)) {
                BigDecimal discountValue = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal amountToPay = p.getPrice().setScale(2, RoundingMode.HALF_EVEN);
                switch (discountType) {
                    case NO_DISCOUNT:
                        break;
                    case LIVE_DISCOUNT:
                        if (((Live) p).getDiscount().getEndTime() > timeUtils.getCurrentTimestamp()) {
                            discountValue = calculateDiscount(p.getPrice(), ((Live) p).getDiscount().getPercentages());
                        }
                        break;
                    case MOVIE_DISCOUNT:
                        if (((Movie) p).getDiscount().getEndTime() > timeUtils.getCurrentTimestamp()) {
                            discountValue = calculateDiscount(p.getPrice(), ((Movie) p).getDiscount().getPercentages());
                        }
                        break;
                }
                amountToPay = amountToPay.subtract(discountValue);
                serviceBankAccount = serviceBankAccount.add(amountToPay);
                sendMoneyToDistributor(p.getTitle(), amountToPay);
            }
        }
    }

    private static BigDecimal calculateDiscount(BigDecimal price, BigDecimal percentages) {
        BigDecimal discountValue = price.setScale(2, RoundingMode.HALF_EVEN);
        discountValue = discountValue.multiply(percentages).setScale(2, RoundingMode.HALF_EVEN);
        discountValue = discountValue.divide(new BigDecimal("100"), RoundingMode.HALF_EVEN);
        return discountValue;
    }

    private static void sendMoneyToDistributor(String title, BigDecimal amountToPay) {
        int distributorId = productDistributorMapping.get(title);
        BigDecimal price = amountToPay;
        for (Distributor d : distributors) {
            if (d.getId() == distributorId) {
                int percentages = d.getContract().getPercentages();
                price = price.multiply(BigDecimal.valueOf(percentages)).setScale(2, RoundingMode.HALF_EVEN);
                price = price.divide(new BigDecimal(100), RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
                d.addSalary(price);
                serviceBankAccount = serviceBankAccount.subtract(price).setScale(2, RoundingMode.HALF_EVEN);
//                System.out.println("Konto: " + serviceBankAccount);
            }
        }
    }

    private static void sendMoneyPerWatchToDistributor(String title) {
        int distributorId = productDistributorMapping.get(title);
        for (Distributor d : distributors) {
            if (d.getId() == distributorId) {
                int pricePerWatch = d.getContract().getPricePerWatch();
                BigDecimal price = new BigDecimal(pricePerWatch).setScale(2, RoundingMode.HALF_EVEN);
                d.addSalary(price);
                serviceBankAccount = serviceBankAccount.subtract(price);
//                System.out.println("Konto: " + serviceBankAccount);
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
    private static void negotiate(Distributor distributor, int p, int w) {
        int sp = random.nextInt(40);
        Contract c = new Contract();
        if (sp > p) {
            c.setPercentages(p);
            c.setPricePerWatch(w);
        } else {
            int m = (p + sp) / 2;
            c.setPercentages(m);
            c.setPricePerWatch(w);
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
        User user = new User();
        usersAmount++;
        user.addOnPaymentListener((p, id, t) -> {
            executePayment(p, id, t);
        });
        user.addOnWatchListener((p) -> {
            sendMoneyPerWatchToDistributor(p);
            int current = monthlyWatchesAmountMap.get(p) + 1;
            monthlyWatchesAmountMap.replace(p, current);
            current = generalWatchesAmountMap.get(p) + 1;
            generalWatchesAmountMap.replace(p, current);
        });
        users.add(user);
        runThread(user);
        if (isUsersViewCreated) {
            onUsersSetChangeListener.onUserCreated(user);
        }
    }

    public static void createNewDistributor() {
        Distributor distributor = new Distributor(semaphore);
        distributorsAmount++;

        distributor.addOnProductReleaseListener((p, id) -> {
            products.add(p);
            productDistributorMapping.put(p.getTitle(), id);
            monthlyWatchesAmountMap.put(p.getTitle(), 0);
            generalWatchesAmountMap.put(p.getTitle(), 0);
            productsWatchesAmountMap.put(p.getTitle(), new ArrayList<>());
            System.out.println(p.getTitle() + ", " + String.valueOf(id));
            movieAmount++;

            if (movieAmount >= 7) { //when id is lower that 7, MainController have not been created yet, so listener is null
                onDatasetChangeListener.notifyDatasetChanged(p);
            }
        });

        distributor.addOnNegotiateListener((p, w) -> {
            negotiate(distributor, p, w);
        });

        distributors.add(distributor);
        runThread(distributor);
        if (isDistributorsViewCreated) {
            onDistributorsSetChangeListener.onDistributorCreated(distributor);
        }
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

    public static void removeUser(String email) {
        for (int i = 0; i < usersAmount; i++) {
            if (users.get(i).getEmail().equals(email)) {
                users.get(i).kill();
                onUsersSetChangeListener.onUserRemoved(users.get(i));
                users.remove(i);
                usersAmount--;
            }
        }
    }

    public static void removeDistributor(String name) {
        for (int i = 0; i < distributorsAmount; i++) {
            if (distributors.get(i).getName().equals(name)) {
                distributors.get(i).kill();
                onDistributorsSetChangeListener.onDistributorRemoved(distributors.get(i));
                distributors.remove(i);
                distributorsAmount--;
            }
        }
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

    public static void setNewPrice(String title, BigDecimal price) {
        products.stream()
                .filter(p -> p.getTitle().equals(title))
                .forEach(p -> p.setPrice(price));
    }

    private void killAllThreads() {
        for (User u : users) {
            u.kill();
        }
        for (Distributor d : distributors) {
            d.kill();
        }
    }

    private void checkIncomes() {
        if (serviceBankAccount.compareTo(new BigDecimal(0)) < 0) {
            negativeIncomes++;
            System.out.println("Negative income");
        } else {
            negativeIncomes = 0;
            System.out.println("Positive income");
        }
        if (negativeIncomes == 3) {
            killAllThreads();
            Controller.forbidAllActions();
        }
    }

    private void resetMonthlyWatchesAmount() {
        for (Product p : products) {
            int currentAmount = monthlyWatchesAmountMap.get(p.getTitle());
            ArrayList<Integer> generalAmount = productsWatchesAmountMap.get(p.getTitle());
            generalAmount.add(currentAmount);
            productsWatchesAmountMap.replace(p.getTitle(), generalAmount);
            monthlyWatchesAmountMap.replace(p.getTitle(), 0);
        }
    }

    /**
     * Initializing lists with some content -
     * creating users and products
     */
    public void initialize() {
        timeUtils.addOnPaymentPeriodListener(() -> {
            collectSubscriptionFee();
            checkIncomes();
            resetMonthlyWatchesAmount();
        });

        for (int i = 0; i < STARTING_USERS_AMOUNT; i++) {
            createNewUser();
        }

        for (int i = 0; i < STARTING_DISTRIBUTORS_AMOUNT; i++) {
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
