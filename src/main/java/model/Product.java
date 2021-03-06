package main.java.model;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import static main.java.model.SimulationSettings.PRODUCT_PRICE_LOWER_BOUND;
import static main.java.model.SimulationSettings.PRODUCT_PRICE_UPPER_BOUND;

public abstract class Product implements Comparable<Product>, Serializable {

    private String title;
    private String image;
    private String description;
    private String productionDate;
    private int duration;
    private String distributor;
    private String country;
    private double rating;
    private BigDecimal price;
    private static Random random = new Random();

    public Product() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Randomizing price in financial mode
     *
     * @return random price
     */
    protected BigDecimal randomizePrice() {
        double d = PRODUCT_PRICE_LOWER_BOUND + (PRODUCT_PRICE_UPPER_BOUND - PRODUCT_PRICE_LOWER_BOUND) * random.nextDouble();
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
        return bd;
    }

    protected void createFromJSON(int id) throws NoMoviesException {
        JSONObject movie = JSONUtils.getSingleMovieFromFile(id);
        setTitle((String) movie.get("title"));
        setProductionDate(String.valueOf(movie.get("year")));
        setCountry(JSONUtils.randCountry());
        setImage((String) movie.get("image"));
        setDescription((String) movie.get("description"));
        setDuration(random.nextInt(60) + 120);
        setRating((random.nextInt(40) / 10.0) + 6.0);
        setPrice(randomizePrice());
    }

    /**
     * Creating random product from secondary JSON file
     *
     * @return method will return ArrayList containing actors in order to use it in derived class
     */
    protected ArrayList<String> createFromSecondaryJSON() {
        JSONObject object = JSONUtils.getSecondaryProductData();
        setTitle((String) object.get("title"));
        setProductionDate(String.valueOf(object.get("year")));
        ArrayList<String> actors = JSONUtils.readActorsFromJSON(object);
        setImage(JSONUtils.randImage());
        setPrice(randomizePrice());
        setRating((random.nextInt(40) / 10.0) + 6.0);
        return actors;
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(o.getRating(), this.rating);
    }
}
