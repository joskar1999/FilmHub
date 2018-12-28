package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.java.model.Product;
import main.java.model.Service;
import main.java.view.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductDataController extends Controller implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Text title;

    @FXML
    private Text rating;

    @FXML
    private Text firstActor;

    @FXML
    private Text secondActor;

    @FXML
    private Text thirdActor;

    private static Product product;
    private ViewUtils utils = new ViewUtils();

    public static void setProduct(Product product) {
        ProductDataController.product = product;
    }

    private void updateUI() {
        title.setText(product.getTitle());
        rating.setText(String.valueOf(product.getRating()));
        image.setImage(new Image(String.valueOf(getClass().getResource(
            "../../resources/images/" + product.getImage()))));
    }

    @FXML
    public void removeProduct() {
        if (Service.getProducts().size() > 6) {
            Service.removeProduct(product.getTitle());
            showNotification("FilmHub", "Produkt usunięty");
            sendToMainPage();
        } else {
            showNotification("FilmHub", "Nie usuwaj wszystkiego! Coś musi zostać!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUI();
    }
}
