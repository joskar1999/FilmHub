package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.java.SimulationAPI;
import main.java.controller.Controller;
import main.java.model.Distributor;

import java.io.IOException;

public class DistributorListViewCell extends ListCell<Distributor> {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text name;

    @FXML
    private Text percentages;

    @FXML
    private ImageView removeDistributorButton;

    private FXMLLoader loader;

    @Override
    protected void updateItem(Distributor item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("../../resources/view/DistributorListCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            name.setText(item.getName());
            percentages.setText(String.valueOf(item.getContract().getPercentages()) + "%");
            removeDistributorButton.setOnMouseClicked(e -> {
                SimulationAPI.removeDistributor(item.getName());
                Controller.showNotification("FilmHub", "Dystrybutor usunięty");
            });

            setText(null);
            setGraphic(anchorPane);
        }
    }
}
