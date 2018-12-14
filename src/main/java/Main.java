package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.Service;

public class Main extends Application {

    private static Stage primaryStage = new Stage();

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/MainView.fxml"));
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.setResizable(false);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        Service service = new Service();
        service.initialize();
        service.start();
        launch(args);
    }
}
