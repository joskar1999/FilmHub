package main.java.model;

import java.io.Serializable;
import java.util.Random;

public abstract class IMDB extends Product implements Serializable {

    private Genre genre;
    private int viewsAmount;
    private Random random = new Random();

    public IMDB() {
        randGenre();
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getViewsAmount() {
        return viewsAmount;
    }

    public void setViewsAmount(int viewsAmount) {
        this.viewsAmount = viewsAmount;
    }

    private void randGenre() {
        int choice = random.nextInt(6);
        switch (choice) {
            case 0:
                genre = Genre.ACTION;
                break;
            case 1:
                genre = Genre.CARTOON;
                break;
            case 2:
                genre = Genre.COMEDY;
                break;
            case 3:
                genre = Genre.DOCUMENT;
                break;
            case 4:
                genre = Genre.DRAMA;
                break;
            case 5:
                genre = Genre.THRILLER;
                break;
        }
    }
}
