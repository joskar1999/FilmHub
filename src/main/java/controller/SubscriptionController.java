package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.model.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SubscriptionController extends Controller {

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

        if (basicPriceInput.getText() != null && !basicPriceInput.getText().trim().isEmpty()
            && validateInput(basicPrice) != null) {
            BigDecimal price = new BigDecimal(Double.valueOf(validateInput(basicPrice)))
                .setScale(2, RoundingMode.HALF_EVEN);
            Service.setSubscriptionPrice(price, null, null);
        }
        if (familyPriceInput.getText() != null && !familyPriceInput.getText().trim().isEmpty()
            && validateInput(familyPrice) != null) {
            BigDecimal price = new BigDecimal(Double.valueOf(validateInput(familyPrice)))
                .setScale(2, RoundingMode.HALF_EVEN);
            Service.setSubscriptionPrice(null, price, null);
        }
        if (premiumPriceInput.getText() != null && !premiumPriceInput.getText().trim().isEmpty()
            && validateInput(premiumPrice) != null) {
            BigDecimal price = new BigDecimal(Double.valueOf(validateInput(premiumPrice)))
                .setScale(2, RoundingMode.HALF_EVEN);
            Service.setSubscriptionPrice(null, null, price);
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
}
