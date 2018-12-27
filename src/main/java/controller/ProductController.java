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
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    protected ListView<Product> listView;

    protected ObservableList<Product> products;
    protected List<Product> allProducts;

    protected ViewUtils utils = new ViewUtils();

    public ProductController() {
        products = FXCollections.observableArrayList();
        allProducts = Service.getProducts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(products);
        listView.setCellFactory(e -> new ProductListViewCell());
    }

    @FXML
    public void sendToMainPage() {
        utils.switchScenes("MainView.fxml");
    }

    @FXML
    public void sendToMoviesPage() {
        utils.switchScenes("MoviesView.fxml");
    }

    @FXML
    public void sendToLivePage() {
        utils.switchScenes("LiveView.fxml");
    }
}
