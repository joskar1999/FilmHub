package main.java.controller;

import main.java.model.Series;
import main.java.model.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class SeriesController extends ProductController {

    public SeriesController() {
        super();
        allProducts
            .stream()
            .filter(p -> p instanceof Series)
            .forEach(p -> products.add(p));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        super.initialize(location, resources);
        Service.addOnDatasetChangeListener((p) -> {
            if (p instanceof Series) {
                products.add(p);
            }
        });
    }
}
