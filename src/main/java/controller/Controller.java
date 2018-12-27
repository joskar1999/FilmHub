package main.java.controller;

import javafx.fxml.FXML;
import main.java.view.ViewUtils;

public class Controller {

    protected ViewUtils utils = new ViewUtils();

    @FXML
    public void sendToMainPage() {
        utils.switchScenes("MainView.fxml");
    }

    @FXML
    public void sendToMoviesPage() {
        utils.switchScenes("MoviesView.fxml");
    }

    @FXML
    public void sendToLivePage() {
        utils.switchScenes("LiveView.fxml");
    }

    @FXML
    public void sendToSeriesPage() {
        utils.switchScenes("SeriesView.fxml");
    }
}
