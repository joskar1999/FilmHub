package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.java.SimulationAPI;
import main.java.model.Distributor;
import main.java.model.OnDistributorsSetChangeListener;
import main.java.model.Service;
import main.java.view.DistributorListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class DistributorController extends Controller implements Initializable {

    @FXML
    private ListView<Distributor> listView;

    private ObservableList<Distributor> distributors;

    public DistributorController() {
        distributors = FXCollections.observableArrayList();
        distributors.addAll(SimulationAPI.getDistributors());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(distributors);
        listView.setCellFactory(e -> new DistributorListViewCell());
        listView.getStylesheets().add(
                getClass().getResource("../../resources/css/styles.css").toExternalForm());

        Service.addOnDistributorsSetChangeListener(new OnDistributorsSetChangeListener() {
            @Override
            public void onDistributorCreated(Distributor distributor) {
                distributors.add(distributor);
            }

            @Override
            public void onDistributorRemoved(Distributor distributor) {
                distributors.remove(distributor);
            }
        });
        Service.isDistributorsViewCreated = true;
    }
}
