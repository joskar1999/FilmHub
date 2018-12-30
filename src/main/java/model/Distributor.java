package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static main.java.model.SimulationSettings.STARTING_PERCENTAGES;
import static main.java.model.SimulationSettings.STARTING_PRICE_PER_WATCH;

public class Distributor implements Runnable {

    private int id;
    private String name;
    private Contract contract;
    private OnProductReleaseListener onProductReleaseListener;
    private OnNegotiateListener onNegotiateListener;
    private Random random;
    private Semaphore semaphore;
    private boolean shouldStop;
    private static int currentId = 0;
    private BigDecimal account;

    public Distributor(Semaphore semaphore) {
        createFromJSON();
        random = new Random();
        this.semaphore = semaphore;
        this.shouldStop = false;
        account = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void addOnProductReleaseListener(OnProductReleaseListener onProductReleaseListener) {
        this.onProductReleaseListener = onProductReleaseListener;
    }

    public void addOnNegotiateListener(OnNegotiateListener onNegotiateListener) {
        this.onNegotiateListener = onNegotiateListener;
    }

    public void kill() {
        this.shouldStop = true;
    }

    public void addSalary(BigDecimal salary) {
        account = account.add(salary).setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Renegotiating Contract, informing Service about it
     */
    private void negotiate() {
        int percentages = random.nextInt(60) + 20;
        int pricePerWatch = random.nextInt(5) + 4;
        onNegotiateListener.onNegotiate(percentages, pricePerWatch);
    }

    /**
     * Creating random distributor, data chosen from fake file
     */
    private void createFromJSON() {
        JSONArray array = JSONUtils.readJSONArray("\\src\\main\\resources\\json\\fakeDistributors.json");
        JSONObject distributor = (JSONObject) array.get(currentId);

        this.id = currentId++;
        this.name = (String) distributor.get("name");
        Contract c = new Contract();
        c.setPercentages(STARTING_PERCENTAGES);
        c.setPricePerWatch(STARTING_PRICE_PER_WATCH);
        this.contract = c;
    }

    /**
     * Releasing new Product by distributor
     */
    private void release() {
        Product p = null;
        int type = random.nextInt(3);
        try {
            if (type == 0) {
                p = new Movie(Service.getMovieAmount());
            } else if (type == 1) {
                p = new Live(Service.getMovieAmount());
            } else if (type == 2) {
                p = new Series();
            }
        } catch (NoMoviesException e) {
            return;
        }
        onProductReleaseListener.onProductRelease(p, id);
    }

    private int randomizeTimeToRelease() {
        int d = random.nextInt(20) + 10;
        int t = d * 24 * 1000;
        t /= (int) Service.getSimulationSettings().getMultiplier();
        return t;
    }

    @Override
    public void run() {
        while (!shouldStop) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            release();
            if (random.nextInt(5) == 2) {
                negotiate();
            }
            semaphore.release();
            try {
                Thread.sleep(randomizeTimeToRelease());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
