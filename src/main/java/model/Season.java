package main.java.model;

import java.util.ArrayList;

public class Season {

    private ArrayList<Episode> episodes;
    private int seasonDuration;

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
}
