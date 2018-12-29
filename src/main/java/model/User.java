package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User implements Runnable {

    private int id;
    private String name;
    private String surname;
    private String dateOfBirth;
    private String email;
    private String cardNumber;
    private SubscriptionType subscriptionType;
    private List<Product> products;
    private Random random;
    private BigDecimal subscriptionPayment;
    private OnPaymentListener onPaymentListener;
    private boolean shouldStop;

    public User(int id) {
        createFromJSON(id);
        random = new Random();
        products = new ArrayList<>();
        subscriptionPayment = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
        subscriptionType = SubscriptionType.NONE;
        shouldStop = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getSubscriptionPayment() {
        return subscriptionPayment;
    }

    public void setSubscriptionPayment(BigDecimal subscriptionPayment) {
        this.subscriptionPayment = subscriptionPayment;
    }

    public void addOnPaymentListener(OnPaymentListener onPaymentListener) {
        this.onPaymentListener = onPaymentListener;
    }

    public void kill() {
        this.shouldStop = true;
    }

    /**
     * Creating random user, data will be chosen from fake file by given id
     *
     * @param id user id 0-999
     */
    private void createFromJSON(int id) {
        JSONArray array = JSONUtils.readJSONArray("\\src\\main\\resources\\json\\fakeUsers.json");
        JSONObject person = (JSONObject) array.get(id);

        this.id = id;
        this.name = (String) person.get("name");
        this.surname = (String) person.get("surname");
        this.email = (String) person.get("email");
        this.cardNumber = (String) person.get("cardNumber");
        this.dateOfBirth = (String) person.get("dateOfBirth");
    }

    /**
     * Checking if object exists in list
     *
     * @param p tested object
     * @return true if exists, false otherwise
     */
    private boolean checkIfProductIsInList(Product p) {
        for (Product elem : products) {
            if (p.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simulated "buy" operation
     * Product is chosen of random from products base
     */
    private void requestProduct() {
        int size = Service.getProducts().size();
        boolean test = false;
        if (size == products.size()) {
            return;
        }
        do {
            int id = random.nextInt(size);
            Product p = Service.getProducts().get(id);
            if (!checkIfProductIsInList(p)) {
                products.add(p);
                int discountType;
                if (p instanceof Live) {
                    discountType = Service.LIVE_DISCOUNT;
                } else if (p instanceof Movie) {
                    discountType = Service.MOVIE_DISCOUNT;
                } else {
                    discountType = Service.NO_DISCOUNT;
                }
                onPaymentListener.onPayment(p.getTitle(), getId(), discountType);
                test = false;
            } else {
                test = true;
            }
        } while (test);
    }

    /**
     * Randomizing sleep time for thread
     *
     * @return time in millis
     */
    private int randomizeTimeToBuy() {
        int d = random.nextInt(6) + 1;
        int t = d * 24 * 1000;
        t /= (int) Service.getSimulationSettings().getMultiplier();
        return t;
    }

    public void randomizeSubscription() {
        int i = random.nextInt(100);
        if (i < 40) {
            subscriptionType = SubscriptionType.NONE;
            setSubscriptionPayment(Service.getSubscription().getPriceMap().get(SubscriptionType.NONE));
        } else if (i < 70) {
            subscriptionType = SubscriptionType.BASIC;
            setSubscriptionPayment(Service.getSubscription().getPriceMap().get(SubscriptionType.BASIC));
        } else if (i < 90) {
            subscriptionType = SubscriptionType.FAMILY;
            setSubscriptionPayment(Service.getSubscription().getPriceMap().get(SubscriptionType.FAMILY));
        } else {
            subscriptionType = SubscriptionType.PREMIUM;
            setSubscriptionPayment(Service.getSubscription().getPriceMap().get(SubscriptionType.PREMIUM));
        }
    }

    @Override
    public void run() {
        while (!shouldStop) {
            if (subscriptionType.equals(SubscriptionType.NONE)) {
                requestProduct();
            }
            try {
                Thread.sleep(randomizeTimeToBuy());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
