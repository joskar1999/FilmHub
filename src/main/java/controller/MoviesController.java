package main.java.controller;

import main.java.model.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class MoviesController extends ProductController {

    public MoviesController() {
        super();
        products.addAll(Service.getProducts());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        Service.addOnDatasetChangeListener((p) -> {
            products.add(p);
        });
    }
}
