package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.java.SimulationAPI;
import main.java.model.Product;
import main.java.model.Service;
import main.java.view.ViewUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    @FXML
    private ImageView seriesImageFirst;

    @FXML
    private Text seriesTitleFirst;

    @FXML
    private Text seriesRatingFirst;

    @FXML
    private Text seriesViewsFirst;

    @FXML
    private ImageView seriesImageSecond;

    @FXML
    private Text seriesTitleSecond;

    @FXML
    private Text seriesRatingSecond;

    @FXML
    private Text seriesViewsSecond;

    @FXML
    private ImageView seriesImageThird;

    @FXML
    private Text seriesTitleThird;

    @FXML
    private Text seriesRatingThird;

    @FXML
    private Text seriesViewsThird;

    @FXML
    private ImageView movieImageFirst;

    @FXML
    private Text movieTitleFirst;

    @FXML
    private Text movieRatingFirst;

    @FXML
    private Text movieViewsFirst;

    @FXML
    private ImageView movieImageSecond;

    @FXML
    private Text movieTitleSecond;

    @FXML
    private Text movieRatingSecond;

    @FXML
    private Text movieViewsSecond;

    @FXML
    private ImageView movieImageThird;

    @FXML
    private Text movieTitleThird;

    @FXML
    private Text movieRatingThird;

    @FXML
    private Text movieViewsThird;

    private ViewUtils utils = new ViewUtils();

    @FXML
    public void refreshMainView() {
        //TODO do refactor - extract method

        List<Product> products = SimulationAPI.getMostPopular(6);
        seriesImageFirst.setImage(new Image(String.valueOf(new File(String.valueOf(
            getClass().getResource("../../resources/images/" + products.get(0).getImage()))))));
        seriesImageSecond.setImage(new Image(String.valueOf(new File(String.valueOf(
            getClass().getResource("../../resources/images/" + products.get(1).getImage()))))));
        seriesImageThird.setImage(new Image(String.valueOf(new File(String.valueOf(
            getClass().getResource("../../resources/images/" + products.get(2).getImage()))))));
        movieImageFirst.setImage(new Image(String.valueOf(new File(String.valueOf(
            getClass().getResource("../../resources/images/" + products.get(3).getImage()))))));
        movieImageSecond.setImage(new Image(String.valueOf(new File(String.valueOf(
            getClass().getResource("../../resources/images/" + products.get(4).getImage()))))));
        movieImageThird.setImage(new Image(String.valueOf(new File(String.valueOf(
            getClass().getResource("../../resources/images/" + products.get(5).getImage()))))));

        seriesTitleFirst.setText(products.get(0).getTitle());
        seriesTitleSecond.setText(products.get(1).getTitle());
        seriesTitleThird.setText(products.get(2).getTitle());
        movieTitleFirst.setText(products.get(3).getTitle());
        movieTitleSecond.setText(products.get(4).getTitle());
        movieTitleThird.setText(products.get(5).getTitle());

        seriesRatingFirst.setText(String.valueOf(products.get(0).getRating()));
        seriesRatingSecond.setText(String.valueOf(products.get(1).getRating()));
        seriesRatingThird.setText(String.valueOf(products.get(2).getRating()));
        movieRatingFirst.setText(String.valueOf(products.get(3).getRating()));
        movieRatingSecond.setText(String.valueOf(products.get(4).getRating()));
        movieRatingThird.setText(String.valueOf(products.get(5).getRating()));

        seriesViewsFirst.setText(String.valueOf(SimulationAPI.getGeneralWatchesAmountMap().get(products.get(0).getTitle())) + " Odsłon");
        seriesViewsSecond.setText(String.valueOf(SimulationAPI.getGeneralWatchesAmountMap().get(products.get(1).getTitle())) + " Odsłon");
        seriesViewsThird.setText(String.valueOf(SimulationAPI.getGeneralWatchesAmountMap().get(products.get(2).getTitle())) + " Odsłon");
        movieViewsFirst.setText(String.valueOf(SimulationAPI.getGeneralWatchesAmountMap().get(products.get(3).getTitle())) + " Odsłon");
        movieViewsSecond.setText(String.valueOf(SimulationAPI.getGeneralWatchesAmountMap().get(products.get(4).getTitle())) + " Odsłon");
        movieViewsThird.setText(String.valueOf(SimulationAPI.getGeneralWatchesAmountMap().get(products.get(5).getTitle())) + " Odsłon");
    }

    @FXML
    public void makeSave() {
        SimulationAPI.serialize();
        showNotification("FilmHub", "Zrobiono sejwa");
    }

    @FXML
    public void loadSave() {
        SimulationAPI.deserialize();
        showNotification("FilmHub", "Wgrano sejwa");
        refreshMainView();
    }

    @FXML
    public void openXKomInBrowser() {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI("https://www.x-kom.pl/g-6/c/31-myszki.html"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        refreshMainView();
        Service.addOnDatasetChangeListener((e) -> {
            refreshMainView();
        });
    }
}
