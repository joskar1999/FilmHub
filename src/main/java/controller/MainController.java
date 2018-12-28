package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.java.model.Product;
import main.java.model.Service;
import main.java.view.ViewUtils;
import org.controlsfx.control.Notifications;

import java.io.File;
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

    @FXML
    private TextField searchBar;

    private ViewUtils utils = new ViewUtils();

    @FXML
    public void refreshMainView() {
        //TODO do refactor - extract method

        List<Product> products = Service.getMostPopular(6);
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
    }

    @FXML
    public void createNewDistributor() {
        Service.createNewDistributor();
        Notifications notifications = Notifications
            .create()
            .title("Filmhub")
            .text("Nowy dystrybutor stworzony!")
            .graphic(null)
            .hideAfter(Duration.seconds(2))
            .position(Pos.BASELINE_RIGHT);
        notifications.showConfirm();
    }

    @FXML
    public void createNewUser() {
        Service.createNewUser();
        Notifications notifications = Notifications
            .create()
            .title("Filmhub")
            .text("Nowy użytkownik stworzony!")
            .graphic(null)
            .hideAfter(Duration.seconds(2))
            .position(Pos.BASELINE_RIGHT);
        notifications.showConfirm();
    }

    @FXML
    public void searchForProduct() {
        String title = searchBar.getText();
        utils.search(title);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshMainView();
        Service.addOnDatasetChangeListener((e) -> {
            refreshMainView();
        });
    }
}
