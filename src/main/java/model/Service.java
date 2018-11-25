package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<User> users = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    public void start() {
        for (int i = 0; i < 20; i++) {
            User user = new User(i);
            users.add(user);
        }

        for (int i = 0; i < 3; i++) {
            Distributor distributor = new Distributor(i);
            distributors.add(distributor);
        }

        for (int i = 0; i < 5; i++) {
            Product product = new Movie(i);
            products.add(product);
        }

        for (User u : users) {
            System.out.println(u.getName());
            System.out.println(u.getCardNumber());
        }

        for (Distributor d : distributors) {
            System.out.println(d.getName());
        }

        for (Product p : products) {
            System.out.println(p.getTitle());
            System.out.println(p.getDescription());
        }
    }

    public static void main(String[] args) {
        Service service = new Service();
        service.start();
    }
}
