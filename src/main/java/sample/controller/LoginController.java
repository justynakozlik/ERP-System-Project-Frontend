package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dto.OperatorCredentialsDto;
import sample.factory.PopupFactory;
import sample.rest.Authenticator;
import sample.rest.AuthenticatorImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane loginAnchorPane;

    private PopupFactory popupFactory;

    private Authenticator authenticator;


    public LoginController() {
        popupFactory = new PopupFactory();
        authenticator = new AuthenticatorImpl();
    }

    public void initialize(URL location, ResourceBundle resources) {
        initializeExitButton();
        initializeLoginButton();
    }

    private void initializeLoginButton() {
        loginButton.setOnAction((x) -> {
            performAuthentication();
        });
    }

    private void initializeExitButton() {
        exitButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) loginAnchorPane.getScene().getWindow();
    }

    private void performAuthentication() {
        Stage waitingPopup = popupFactory.createWaitingPopup("Conecting to the server...");
        waitingPopup.show();

        String loginText = loginTextField.getText();
        String passwordText = passwordTextField.getText();

        OperatorCredentialsDto dto = new OperatorCredentialsDto();
        dto.setLogin(loginText);
        dto.setPassword(passwordText);

        authenticator.authenticate(dto, (authenticationResult) -> {
            Platform.runLater(() -> {
                waitingPopup.close();
                if(authenticationResult.isAuthenticated()){
                    openAppAndCloseLoginStage();
                }else {
                    showIncorrectCredentialsMessage();
                }
            });
        });
    }

    private void showIncorrectCredentialsMessage() {
        //TODO
        System.out.println("Incorrect credentials");
    }

    private void openAppAndCloseLoginStage(){

        Stage appStage = new Stage();
        Parent appRoot = null;

        try {
            appRoot = FXMLLoader.load(getClass().getResource("/fxml/app.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(appRoot, 1024, 768);
        appStage.setTitle("ERP System");
        appStage.setScene(scene);
        appStage.show();
        getStage().close();
    }
}


