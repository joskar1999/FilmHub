package main.java.model;

import java.util.ArrayList;
import java.util.Random;

public class Season {

    private ArrayList<Episode> episodes = new ArrayList<>();
    private int seasonDuration;
    private Random random = new Random();

    public Season() {
        createSeason();
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public int getSeasonDuration() {
        return seasonDuration;
    }

    public void setSeasonDuration(int seasonDuration) {
        this.seasonDuration = seasonDuration;
    }

    private void createSeason() {
        int numberOfEpisodes = random.nextInt(4) + 4;
        for (int i = 0; i < numberOfEpisodes; i++) {
            episodes.add(new Episode());
        }
    }
}
