package main.java.model;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Series extends IMDB {

    private ArrayList<Season> seasons = new ArrayList<>();
    private ArrayList<String> actors;
    private Random random = new Random();

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
        //TODO do refactor
        JSONObject object = JSONUtils.getSecondaryProductData();
        setTitle((String) object.get("title"));
        setProductionDate(String.valueOf(object.get("year")));
        actors = JSONUtils.readActorsFromJSON(object);
        setImage("emrata.jpeg");
        setPrice(randomizePrice());
        setRating((random.nextInt(40) / 10.0) + 6.0);
    }
}
