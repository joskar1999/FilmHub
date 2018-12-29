package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.model.OnUsersSetChangeListener;
import main.java.model.Service;
import main.java.model.User;
import main.java.view.UserListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController extends Controller implements Initializable {

    @FXML
    private ListView<User> listView;

    private ObservableList<User> users;

    public UsersController() {
        users = FXCollections.observableArrayList();
        users.addAll(Service.getUsers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(users);
        listView.setCellFactory(e -> new UserListViewCell());
        listView.getStylesheets().add(
            getClass().getResource("../../resources/css/styles.css").toExternalForm());

        Service.addOnUsersSetChangeListener(new OnUsersSetChangeListener() {
            @Override
            public void onUserCreated(User user) {
                users.add(user);
            }

            @Override
            public void onUserRemoved(User user) {
                users.remove(user);
            }
        });
    }
}
