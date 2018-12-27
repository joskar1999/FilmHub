package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.model.Product;
import main.java.model.Service;
import main.java.view.ProductListViewCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController extends Controller implements Initializable {

    @FXML
    protected ListView<Product> listView;

    protected ObservableList<Product> products;
    protected List<Product> allProducts;

    public ProductController() {
        products = FXCollections.observableArrayList();
        allProducts = Service.getProducts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(products);
        listView.setCellFactory(e -> new ProductListViewCell());
        listView.getStylesheets().add(
            getClass().getResource("../../resources/css/styles.css").toExternalForm());
    }
}
