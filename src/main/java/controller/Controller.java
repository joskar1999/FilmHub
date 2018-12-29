package main.java.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import main.java.model.Service;
import main.java.view.ViewUtils;
import org.controlsfx.control.Notifications;

public class Controller {

    @FXML
    private TextField searchBar;

    protected ViewUtils utils = new ViewUtils();

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
    public void searchForProduct() {
        String title = searchBar.getText();
        utils.search(title);
    }

    @FXML
    public void createNewDistributor() {
        Service.createNewDistributor();
        showNotification("Filmhub", "Nowy dystrybutor stworzony!");
    }

    @FXML
    public void createNewUser() {
        Service.createNewUser();
        showNotification("Filmhub", "Nowy użytkownik stworzony!");
    }

    protected void showNotification(String title, String message) {
        Notifications notifications = Notifications
            .create()
            .title(title)
            .text(message)
            .graphic(null)
            .hideAfter(Duration.seconds(2))
            .position(Pos.BASELINE_RIGHT);
        notifications.showConfirm();
    }
}
