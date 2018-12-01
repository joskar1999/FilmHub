package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Movie extends IMDB {

    private ArrayList<String> trailers;
    private ArrayList<Actor> actors;
    private long timeToWatch;
    private Discount discount;

    public Movie(int id) throws NoMoviesException {
        createFromJSON(id);
    }

    public ArrayList<String> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<String> trailers) {
        this.trailers = trailers;
    }

    public long getTimeToWatch() {
        return timeToWatch;
    }

    public void setTimeToWatch(long timeToWatch) {
        this.timeToWatch = timeToWatch;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Creating random movie from given id
     *
     * @param id value between 0-6
     */
    private void createFromJSON(int id) throws NoMoviesException {
        JSONArray array = Utils.readJSONArray("\\src\\main\\resources\\json\\movieList.json");
        if (array.size() <= id) {
            throw new NoMoviesException();
        }
        JSONObject movie = (JSONObject) array.get(id);
        Random random = new Random();

        setTitle((String) movie.get("title"));
        setProductionDate(String.valueOf(movie.get("year")));
        setCountry(Utils.randCountry());
        setImage((String) movie.get("image"));
        setDescription((String) movie.get("description"));
        setDuration(random.nextInt(60) + 120);
        setRating((random.nextInt(40) / 10.0) + 6.0);
        setPrice(randomizePrice());
    }
}
