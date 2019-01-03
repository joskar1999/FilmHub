package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.SimulationAPI;
import main.java.model.SubscriptionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class SubscriptionController extends Controller implements Initializable {

    @FXML
    private TextField basicPriceInput;

    @FXML
    private TextField familyPriceInput;

    @FXML
    private TextField premiumPriceInput;

    private String basicPrice;
    private String familyPrice;
    private String premiumPrice;

    @FXML
    public void applyChanges() {
        basicPrice = basicPriceInput.getText();
        familyPrice = familyPriceInput.getText();
        premiumPrice = premiumPriceInput.getText();
        boolean isChange = false;

        if (basicPriceInput.getText() != null && !basicPriceInput.getText().trim().isEmpty()
                && validateInput(basicPrice) != null) {
            BigDecimal price = new BigDecimal(Double.valueOf(validateInput(basicPrice)))
                    .setScale(2, RoundingMode.HALF_EVEN);
            SimulationAPI.setSubscriptionPrice(price, null, null);
            isChange = true;
        }
        if (familyPriceInput.getText() != null && !familyPriceInput.getText().trim().isEmpty()
                && validateInput(familyPrice) != null) {
            BigDecimal price = new BigDecimal(Double.valueOf(validateInput(familyPrice)))
                    .setScale(2, RoundingMode.HALF_EVEN);
            SimulationAPI.setSubscriptionPrice(null, price, null);
            isChange = true;
        }
        if (premiumPriceInput.getText() != null && !premiumPriceInput.getText().trim().isEmpty()
                && validateInput(premiumPrice) != null) {
            BigDecimal price = new BigDecimal(Double.valueOf(validateInput(premiumPrice)))
                    .setScale(2, RoundingMode.HALF_EVEN);
            SimulationAPI.setSubscriptionPrice(null, null, price);
            isChange = true;
        }
        if (isChange) {
            showNotification("FilmHub", "Ceny subskrypcji zaktualizowane");
        }
    }

    /**
     * Checking if given String is numeric
     *
     * @param input String to be checked
     * @return null if not, otherwise this String with correct decimal separator
     */
    private String validateInput(String input) {
        input = input.replace(',', '.');
        input = input.replaceAll("\\s+", "");
        input = input.replaceAll("[A-Za-z]+", "");
        if (!input.matches("-?\\d+(\\.\\d+)?")) {
            return null;
        }
        return input;
    }

    private void updateUI() {
        basicPriceInput.setPromptText(String.valueOf(
                SimulationAPI.getSubscription().getPriceMap().get(SubscriptionType.BASIC) + " PLN"));
        familyPriceInput.setPromptText(String.valueOf(
                SimulationAPI.getSubscription().getPriceMap().get(SubscriptionType.FAMILY) + " PLN"));
        premiumPriceInput.setPromptText(String.valueOf(
                SimulationAPI.getSubscription().getPriceMap().get(SubscriptionType.PREMIUM) + " PLN"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        updateUI();
    }
}
