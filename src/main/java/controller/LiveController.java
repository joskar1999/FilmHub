package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.model.Product;
import main.java.model.Service;
import main.java.view.ProductListViewCell;
import main.java.view.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

//TODO make inheritance, a lot of in common with MoviesController
public class LiveController implements Initializable {

    @FXML
    private ListView<Product> listView;

    private ObservableList<Product> products;

    private ViewUtils utils = new ViewUtils();

    public LiveController() {
        products = FXCollections.observableArrayList();
        products.addAll(Service.getProducts());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(products);
        listView.setCellFactory(e -> new ProductListViewCell());

        Service.addOnDatasetChangeListener(p -> {
            products.add(p);
        });
    }

    @FXML
    public void sendToMainPage() {
        utils.switchScenes("MainView.fxml");
    }

    @FXML
    public void sendToMoviesPage() {
        utils.switchScenes("MoviesView.fxml");
    }
}
