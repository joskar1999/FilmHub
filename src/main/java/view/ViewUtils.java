package main.java.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.Main;
import main.java.controller.ProductDataController;
import main.java.model.Product;
import main.java.model.Service;

import java.io.IOException;

public class ViewUtils {

    public void switchScenes(String fxmlFile) {
        Parent root = null;
        Stage stage = Main.getPrimaryStage();
        fxmlFile = "../../resources/view/" + fxmlFile;
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 1366, 768));
    }

    public void search(String title) {
        Product product = Service.searchForProduct(title);
        if (product != null) {
            ProductDataController.setProduct(product);
            switchScenes("ProductDataView.fxml");
        }
    }
}
