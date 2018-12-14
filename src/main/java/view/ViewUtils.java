package main.java.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.Main;

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
}
