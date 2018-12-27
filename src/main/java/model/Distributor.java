package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Distributor implements Runnable {

    private int id;
    private String name;
    private Contract contract;
    private OnProductReleaseListener onProductReleaseListener;
    private OnNegotiateListener onNegotiateListener;
    private Random random;
    private Semaphore semaphore;

    public Distributor(int id, Semaphore semaphore) {
        createFromJSON(id);
        random = new Random();
        this.semaphore = semaphore;
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

    /**
     * Renegotiating Contract, informing Service about it
     */
    private void negotiate() {
        int percentages = random.nextInt(60) + 20;
        onNegotiateListener.onNegotiate(percentages);
    }

    /**
     * Creating random distributor, data chosen from fake file
     *
     * @param id random value between 0-99
     */
    private void createFromJSON(int id) {
        JSONArray array = JSONUtils.readJSONArray("\\src\\main\\resources\\json\\fakeDistributors.json");
        JSONObject distributor = (JSONObject) array.get(id);

        this.id = id;
        this.name = (String) distributor.get("name");
        Contract c = new Contract();
        c.setPercentages(25);
        this.contract = c;
    }

    /**
     * Releasing new Product by distributor
     */
    private void release() {
        Product p = null;
        try {
            p = new Movie(Service.getMovieAmount());
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
        while (true) {
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
