package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.rest.ItemRestClient;
import sample.table.ItemTableModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteItemController implements Initializable {

    @FXML
    private BorderPane deleteItemBoarderPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    private final ItemRestClient itemRestClient;

    private Long idItemToDelete;


    public DeleteItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeDeleteButton();
    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction((x) -> {
            Thread thread = new Thread(() -> {
                itemRestClient.deleteItem(idItemToDelete);
                Platform.runLater(() -> {
                    getStage().close();
                });
            });
            thread.start();
        });
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    public void loadItem(ItemTableModel itemTableModel){
        nameLabel.setText(itemTableModel.getName());
        quantityLabel.setText(itemTableModel.getQuantity() + " " + itemTableModel.getQuantityType());
        this.idItemToDelete = itemTableModel.getIdItem();
    }

    private Stage getStage() {
        return (Stage) deleteItemBoarderPane.getScene().getWindow();
    }
}


