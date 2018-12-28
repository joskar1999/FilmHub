package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.java.model.Live;
import main.java.model.Movie;
import main.java.model.Product;
import main.java.model.Series;

import java.io.IOException;

public class ProductListViewCell extends ListCell<Product> {

    @FXML
    private Text title;

    @FXML
    private Text rating;

    @FXML
    private Text firstActor;

    @FXML
    private Text secondActor;

    @FXML
    private Text thirdActor;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader loader;
    private ViewUtils utils = new ViewUtils();

    @Override
    protected void updateItem(Product item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("../../resources/view/ListCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            title.setText(item.getTitle());
            imageView.setImage(new Image(String.valueOf(getClass().getResource(
                "../../resources/images/" + item.getImage()))));
            rating.setText(String.valueOf(item.getRating()));

            title.setOnMouseClicked(e -> {
                utils.search(item.getTitle());
            });

            if (item instanceof Movie) {
                firstActor.setText(((Movie) item).getActors().get(0));
                secondActor.setText(((Movie) item).getActors().get(1));
                thirdActor.setText(((Movie) item).getActors().get(2));
            } else if (item instanceof Live) {
                firstActor.setText("");
                secondActor.setText("");
                thirdActor.setText("");
            } else if (item instanceof Series) {
                firstActor.setText(((Series) item).getActors().get(0));
                secondActor.setText(((Series) item).getActors().get(1));
                thirdActor.setText(((Series) item).getActors().get(2));
            }
            setText(null);
            setGraphic(anchorPane);
        }
    }
}
