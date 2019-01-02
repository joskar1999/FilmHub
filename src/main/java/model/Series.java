package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Series extends IMDB implements Serializable {

    private ArrayList<Season> seasons = new ArrayList<>();
    private ArrayList<String> actors;
    private static Random random = new Random();

    public Series() {
        createSeries();
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    private void createSeries() {
        int numberOfSeasons = random.nextInt(5) + 1;
        for (int i = 0; i < numberOfSeasons; i++) {
            seasons.add(new Season());
        }
        actors = createFromSecondaryJSON();
    }
}
