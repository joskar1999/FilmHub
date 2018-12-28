package main.java.model;

public class Live extends Product {

    private long startTime;
    private Discount discount;

    public Live(int id) throws NoMoviesException {
        createFromJSON(id);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    protected void createFromJSON(int id) throws NoMoviesException {
        super.createFromJSON(id);
    }
}
