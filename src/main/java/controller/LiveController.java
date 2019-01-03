package main.java.controller;

import main.java.SimulationAPI;
import main.java.model.Live;
import main.java.model.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class LiveController extends ProductController {

    public LiveController() {
        super();
        allProducts
            .stream()
            .filter(p -> p instanceof Live)
            .forEach(p -> products.add(p));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        Service.addOnDatasetChangeListener(p -> {
            if (p instanceof Live) {
                products.add(p);
            }
        });
    }
}
