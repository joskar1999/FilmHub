package main.java.model;

public class Episode {

    private String name;
    private int duration;
    private long premiere;

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

    public long getPremiere() {
        return premiere;
    }

    public void setPremiere(long premiere) {
        this.premiere = premiere;
    }
}
