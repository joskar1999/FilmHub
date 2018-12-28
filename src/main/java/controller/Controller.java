package main.java.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.util.Duration;
import main.java.view.ViewUtils;
import org.controlsfx.control.Notifications;

public class Controller {

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
