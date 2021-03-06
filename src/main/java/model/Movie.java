package main.java.model;

import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie extends IMDB implements Serializable {

    private ArrayList<String> trailers;
    private ArrayList<String> actors;
    private long timeToWatch;
    private Discount discount;

    public Movie(int id) throws NoMoviesException {
        createFromJSON(id);
        discount = new Discount();
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

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * Creating random movie from given id
     *
     * @param id movie identifier
     */
    @Override
    protected void createFromJSON(int id) throws NoMoviesException {
        JSONObject movie = null;
        try {
            super.createFromJSON(id);
            movie = JSONUtils.getSingleMovieFromFile(id);
            actors = JSONUtils.readActorsFromJSON(movie);
        } catch (NoMoviesException e) {
            actors = createFromSecondaryJSON();
        }
    }
}
