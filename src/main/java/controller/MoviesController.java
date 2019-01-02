package main.java.controller;

import main.java.SimulationAPI;
import main.java.model.Movie;
import main.java.model.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class MoviesController extends ProductController {

    public MoviesController() {
        super();
        allProducts
                .stream()
                .filter(p -> p instanceof Movie)
                .forEach(p -> products.add(p));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        Service.addOnDatasetChangeListener((p) -> {
            if (p instanceof Movie) {
                products.add(p);
            }
        });
    }
}
