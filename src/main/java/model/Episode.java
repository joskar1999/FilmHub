package main.java.model;

import main.java.SimulationAPI;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Random;

public class Episode implements Serializable {

    private String name;
    private int duration;
    private String premiere;
    private static Random random = new Random();

    public Episode() {
        if (SimulationAPI.getMovieAmount() > 6) {
            createFromJSON();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    private void createFromJSON() {
        JSONObject object = JSONUtils.getSecondaryProductData();
        this.name = (String) object.get("title");
        this.premiere = String.valueOf(object.get("year"));
        this.duration = random.nextInt(30) + 30;
    }
}
