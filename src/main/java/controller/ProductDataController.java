package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.java.model.*;
import main.java.view.ViewUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @FXML
    private TextField discountPercentages;

    @FXML
    private TextField discountDuration;

    @FXML
    private Text percentagesText;

    @FXML
    private Text durationText;

    @FXML
    private AnchorPane percentagesAnchor;

    @FXML
    private AnchorPane durationAnchor;

    @FXML
    private ImageView startDiscountButton;

    @FXML
    private TextField priceTextField;

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
        priceTextField.setPromptText(product.getPrice() + " PLN");
        if (product instanceof Movie) {
            firstActor.setText(((Movie) product).getActors().get(0));
            thirdActor.setText(((Movie) product).getActors().get(2));
            secondActor.setText(((Movie) product).getActors().get(1));
        } else if (product instanceof Series) {
            firstActor.setText(((Series) product).getActors().get(0));
            thirdActor.setText(((Series) product).getActors().get(2));
            secondActor.setText(((Series) product).getActors().get(1));
        } else if (product instanceof Live) {
            firstActor.setText("");
            secondActor.setText("");
            thirdActor.setText("");
        } //TODO do refactor - duplicate
    }

    private String validatePercentages(String input) {
        input = input.replace(',', '.');
        input = input.replaceAll("\\s+", "");
        input = input.replaceAll("[%]", "");
        if (input.matches("-?\\d+(\\.\\d+)?")) {
            if (Double.valueOf(input) < 5.00 || Double.valueOf(input) > 50.00) {
                return null;
            }
        } else {
            return null;
        }
        return input;
    }

    private int validateDuration(String input) {
        input = input.replaceAll("[A-Za-z]+", "");
        input = input.replaceAll("\\s+", "");
        if (!input.matches("[0-9]+")) {
            return 0;
        }
        return Integer.valueOf(input);
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

    @FXML
    public void makeDiscount() {
        Discount discount = new Discount();
        if (validateDuration(discountDuration.getText()) != 0
                && validatePercentages(discountPercentages.getText()) != null) {
            int d = validateDuration(discountDuration.getText());
            double p = Double.valueOf(validatePercentages(discountPercentages.getText()));
            discount.setPercentages(new BigDecimal(p).setScale(2, RoundingMode.HALF_EVEN));
            discount.setStartTime(Service.getTimeUtils().getCurrentTimestamp());
            discount.setEndTime(Service.getTimeUtils().getCurrentTimestamp() + d * 24 * 3600);
            if (product instanceof Live) {
                Service.addDiscount(product.getTitle(), discount, Service.LIVE_DISCOUNT);
            } else if (product instanceof Movie) {
                Service.addDiscount(product.getTitle(), discount, Service.MOVIE_DISCOUNT);
            }
            showNotification("FilmHub", "Promocja utworzona");
        }
    }

    @FXML
    public void setNewPrice() {
        String input = priceTextField.getText();
        input = input.replace(',', '.');
        input = input.replaceAll("\\s+", "");
        input = input.replaceAll("[A-Za-z]+", "");
        if (input.matches("-?\\d+(\\.\\d+)?")) {
            BigDecimal newPrice = new BigDecimal(Double.valueOf(input)).setScale(2, RoundingMode.HALF_EVEN);
            Service.setNewPrice(product.getTitle(), newPrice);
            showNotification("FilmHub", "Nowa cena ustawiona");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUI();
        if (product instanceof Series) {
            startDiscountButton.setVisible(false);
            percentagesAnchor.setVisible(false);
            durationAnchor.setVisible(false);
            percentagesText.setVisible(false);
            durationText.setVisible(false);
        }
    }
}
