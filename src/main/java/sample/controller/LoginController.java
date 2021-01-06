package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button exitButton;

    @FXML
    public Button loginButton;

    public void initialize(URL location, ResourceBundle resources) {

        exitButton.setOnAction((x) -> {
            System.out.println("exitButton action");
        });
    }
}


