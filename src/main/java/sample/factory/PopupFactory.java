package sample.factory;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupFactory {

    public Stage createWaitingPopup(String text) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        VBox pane = new VBox();
        pane.setStyle(waitingPopupSetStyle());
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);

        Label label = new Label(text);
        label.setStyle(labelSetStyle());
        ProgressBar progressBar = new ProgressBar();

        pane.getChildren().addAll(label, progressBar);

        stage.setScene(new Scene(pane, 200, 100));
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    private String labelSetStyle() {
        return "-fx-text-fill: #003366;";
    }

    private String waitingPopupSetStyle() {
        return "-fx-background-color: #c7c7c7; -fx-border-color: #003366;";
    }
}
