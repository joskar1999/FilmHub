package main.java.model;

import java.util.ArrayList;

public class Series extends IMDB {

    private ArrayList<Season> seasons;
    private ArrayList<Actor> actors;

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }
}
