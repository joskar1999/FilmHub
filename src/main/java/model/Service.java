package main.java.model;

import main.java.controller.Controller;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Semaphore;

import static main.java.model.SimulationSettings.STARTING_DISTRIBUTORS_AMOUNT;
import static main.java.model.SimulationSettings.STARTING_USERS_AMOUNT;

public class Service implements Serializable {

    private List<User> users = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private Map<String, Integer> productDistributorMapping = new HashMap<>();
    private Map<String, Integer> monthlyWatchesAmountMap = new HashMap<>();
    private Map<String, Integer> generalWatchesAmountMap = new HashMap<>();
    private Map<String, ArrayList<Integer>> productsWatchesAmountMap = new HashMap<>();
    private SimulationSettings simulationSettings = new SimulationSettings();
    private transient TimeUtils timeUtils = new TimeUtils();
    private Subscription subscription = new Subscription();
    private BigDecimal serviceBankAccount = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
    private int movieAmount = 0;
    private int usersAmount = 0;
    private int distributorsAmount = 0;
    private transient Semaphore semaphore;
    private static Random random = new Random();
    private static OnDatasetChangeListener onDatasetChangeListener;
    private static OnUsersSetChangeListener onUsersSetChangeListener;
    private static OnDistributorsSetChangeListener onDistributorsSetChangeListener;
    private static OnSimulationEndListener onSimulationEndListener;
    public static boolean isUsersViewCreated = false;
    public static boolean isDistributorsViewCreated = false;
    public static final int NO_DISCOUNT = 0;
    public static final int LIVE_DISCOUNT = 1;
    public static final int MOVIE_DISCOUNT = 2;
    private int negativeIncomes = 0;

    public Service() {
        simulationSettings.setMultiplier(20);
        semaphore = new Semaphore(1);
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public TimeUtils getTimeUtils() {
        return timeUtils;
    }

    public void setSubscriptionPrice(BigDecimal basic, BigDecimal family, BigDecimal premium) {
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

    public List<Product> getProducts() {
        return products;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public SimulationSettings getSimulationSettings() {
        return simulationSettings;
    }

    public Map<String, Integer> getGeneralWatchesAmountMap() {
        return generalWatchesAmountMap;
    }

    public int getMovieAmount() {
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

    public static void addOnSimulationEndListener(OnSimulationEndListener onSimulationEndListener) {
        Service.onSimulationEndListener = onSimulationEndListener;
    }

    /**
     * Sending money to distributor, increasing value of
     * Service 'bank account'; Calculating discount
     */
    private synchronized void executePayment(String product, int userId, final int discountType) {
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
                        generalWatchesAmountMap.replace(product, generalWatchesAmountMap.get(product) + 1);
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

    private BigDecimal calculateDiscount(BigDecimal price, BigDecimal percentages) {
        BigDecimal discountValue = price.setScale(2, RoundingMode.HALF_EVEN);
        discountValue = discountValue.multiply(percentages).setScale(2, RoundingMode.HALF_EVEN);
        discountValue = discountValue.divide(new BigDecimal("100"), RoundingMode.HALF_EVEN);
        return discountValue;
    }

    private void sendMoneyToDistributor(String title, BigDecimal amountToPay) {
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

    private void sendMoneyPerWatchToDistributor(String title) {
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
    private void negotiate(Distributor distributor, int p, int w) {
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
    public List<Product> getMostPopular(int amount) {
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

    public void createNewUser() {
        User user = new User();
        usersAmount++;
        addUsersListeners(user);
        users.add(user);
        runThread(user);
        if (isUsersViewCreated) {
            onUsersSetChangeListener.onUserCreated(user);
        }
    }

    public void createNewDistributor() {
        Distributor distributor = new Distributor(semaphore);
        distributorsAmount++;

        addDistributorsListeners(distributor);

        distributors.add(distributor);
        runThread(distributor);
        if (isDistributorsViewCreated) {
            onDistributorsSetChangeListener.onDistributorCreated(distributor);
        }
    }

    private void runThread(Runnable r) {
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
    public Product searchForProduct(String title) {
        for (Product p : products) {
            if (p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }

    public void removeProduct(String title) {
        products.removeIf(p -> p.getTitle().equals(title));
        //Product will be removed only from 'products'
        //because it may cause NullPointerException
        //in User object if it will be removed from other List
    }

    public void removeUser(String email) {
        for (int i = 0; i < usersAmount; i++) {
            if (users.get(i).getEmail().equals(email)) {
                users.get(i).kill();
                onUsersSetChangeListener.onUserRemoved(users.get(i));
                users.remove(i);
                usersAmount--;
            }
        }
    }

    public void removeDistributor(String name) {
        for (int i = 0; i < distributorsAmount; i++) {
            if (distributors.get(i).getName().equals(name)) {
                distributors.get(i).kill();
                onDistributorsSetChangeListener.onDistributorRemoved(distributors.get(i));
                distributors.remove(i);
                distributorsAmount--;
            }
        }
    }

    public void addDiscount(String productTitle, Discount discount, int type) {
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

    public void setNewPrice(String title, BigDecimal price) {
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

    /**
     * This method should be used to restore simulation
     * after loading previous save
     */
    private void runAllThreads() {
        for (User u : users) {
            addUsersListeners(u);
            runThread(u);
        }
        for (Distributor d : distributors) {
            addDistributorsListeners(d);
            runThread(d);
        }
    }

    private void addDistributorsListeners(Distributor d) {
        d.addOnProductReleaseListener((p, id) -> {
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

        d.addOnNegotiateListener((p, w) -> {
            negotiate(d, p, w);
        });
    }

    private void addUsersListeners(User u) {
        u.addOnPaymentListener((p, id, t) -> {
            executePayment(p, id, t);
        });
        u.addOnWatchListener((p) -> {
            sendMoneyPerWatchToDistributor(p);
            int current = monthlyWatchesAmountMap.get(p) + 1;
            monthlyWatchesAmountMap.replace(p, current);
            current = generalWatchesAmountMap.get(p) + 1;
            generalWatchesAmountMap.replace(p, current);
        });
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
            onSimulationEndListener.onSimulationEnd();
        }
    }

    public void serialize() {
        serializeObject("users.ser", users);
        serializeObject("distributors.ser", distributors);
        serializeObject("products.ser", products);
        serializeObject("generalWatchesAmountMap.ser", generalWatchesAmountMap);
        serializeObject("monthlyWatchesAmountMap.ser", monthlyWatchesAmountMap);
        serializeObject("productsWatchesAmountMap.ser", productsWatchesAmountMap);
        serializeObject("productDistributorMapping.ser", productDistributorMapping);
        serializeObject("subscription.ser", subscription);
        serializeObject("movieAmount.ser", movieAmount);
        serializeObject("usersAmount.ser", usersAmount);
        serializeObject("distributorsAmount.ser", distributorsAmount);
        serializeObject("serviceBankAccount.ser", serviceBankAccount);
        serializeObject("negativeIncomes.ser", negativeIncomes);
    }

    public void deserialize() {
        killAllThreads();
        users = (List<User>) deserializeObject("users.ser");
        distributors = (List<Distributor>) deserializeObject("distributors.ser");
        products = (List<Product>) deserializeObject("products.ser");
        generalWatchesAmountMap = (Map<String, Integer>) deserializeObject("generalWatchesAmountMap.ser");
        monthlyWatchesAmountMap = (Map<String, Integer>) deserializeObject("monthlyWatchesAmountMap.ser");
        productsWatchesAmountMap = (Map<String, ArrayList<Integer>>) deserializeObject("productsWatchesAmountMap.ser");
        productDistributorMapping = (Map<String, Integer>) deserializeObject("productDistributorMapping.ser");
        subscription = (Subscription) deserializeObject("subscription.ser");
        movieAmount = (int) deserializeObject("movieAmount.ser");
        usersAmount = (int) deserializeObject("usersAmount.ser");
        distributorsAmount = (int) deserializeObject("distributorsAmount.ser");
        serviceBankAccount = (BigDecimal) deserializeObject("serviceBankAccount.ser");
        negativeIncomes = (int) deserializeObject("negativeIncomes.ser");
        runAllThreads();
    }

    private void serializeObject(String fileName, Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                    new FileOutputStream("src/main/resources/serialized/" + fileName)));
            out.writeObject(object);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object deserializeObject(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                    new FileInputStream("src/main/resources/serialized/" + fileName)));
            Object o = in.readObject();
            in.close();
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

        }
        return null;
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
}
