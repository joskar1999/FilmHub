package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.java.model.Service;
import main.java.model.User;

import java.io.IOException;

public class UserListViewCell extends ListCell<User> {

    @FXML
    private Text name;

    @FXML
    private Text email;

    @FXML
    private ImageView removeUserButton;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader loader;

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("../../resources/view/UserListCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            name.setText(item.getName() + " " + item.getSurname());
            email.setText(item.getEmail());
            removeUserButton.setOnMouseClicked(e -> {
                Service.removeUser(item.getEmail());
            });

            setText(null);
            setGraphic(anchorPane);
        }
    }
}
