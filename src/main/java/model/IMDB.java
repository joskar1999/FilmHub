package main.java.model;

public abstract class IMDB extends Product {

    private Genre genre;
    private int viewsAmount;

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
}
