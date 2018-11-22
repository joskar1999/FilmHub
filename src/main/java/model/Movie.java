package main.java.model;

import java.util.ArrayList;

public class Movie extends IMDB {

    private ArrayList<String> trailers;
    private ArrayList<Actor> actors;
    private long timeToWatch;
    private Discount discount;

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
}
