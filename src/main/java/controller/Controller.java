package main.java.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import main.java.SimulationAPI;
import main.java.model.Service;
import main.java.view.ViewUtils;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField searchBar;

    protected ViewUtils utils = new ViewUtils();
    private static boolean isActionAllowed = true;

    @FXML
    protected void sendToMainPage() {
        utils.switchScenes("MainView.fxml");
    }

    @FXML
    protected void sendToMoviesPage() {
        utils.switchScenes("MoviesView.fxml");
    }

    @FXML
    protected void sendToLivePage() {
        utils.switchScenes("LiveView.fxml");
    }

    @FXML
    protected void sendToSeriesPage() {
        utils.switchScenes("SeriesView.fxml");
    }

    @FXML
    protected void sendToSubscriptionPage() {
        utils.switchScenes("SubscriptionView.fxml");
    }

    @FXML
    protected void sendToClientsPage() {
        utils.switchScenes("UsersView.fxml");
    }

    @FXML
    protected void sendToDistributorsPage() {
        utils.switchScenes("DistributorView.fxml");
    }

    @FXML
    protected void sendToSimulationEndPage() {
        utils.switchScenes("SimulationEndView.fxml");
    }

    @FXML
    public void searchForProduct() {
        String title = searchBar.getText();
        utils.search(title);
    }

    @FXML
    public void createNewDistributor() {
        if (isActionAllowed) {
            SimulationAPI.createNewDistributor();
            showNotification("Filmhub", "Nowy dystrybutor stworzony!");
        }
    }

    @FXML
    public void createNewUser() {
        if (isActionAllowed) {
            SimulationAPI.createNewUser();
            showNotification("Filmhub", "Nowy użytkownik stworzony!");
        }
    }

    public static void showNotification(String title, String message) {
        Notifications notifications = Notifications
            .create()
            .title(title)
            .text(message)
            .graphic(null)
            .hideAfter(Duration.seconds(2))
            .position(Pos.BASELINE_RIGHT);
        notifications.showConfirm();
    }

    public static void forbidAllActions() {
        isActionAllowed = false;
        //TODO show popup informing about simulation end
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Service.addOnSimulationEndListener(() -> {
            Platform.runLater(() -> {
                sendToSimulationEndPage();
            });
        });
    }
}
