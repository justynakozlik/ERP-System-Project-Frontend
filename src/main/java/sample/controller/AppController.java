package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.table.EmployeeTableModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private TableView<EmployeeTableModel> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("firstName"));

        TableColumn secondNameColumn = new TableColumn("Second Name");
        secondNameColumn.setMinWidth(100);
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("lastName"));

        TableColumn salaryColumn = new TableColumn("Salary");
        salaryColumn.setMinWidth(100);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("salary"));

        tableView.getColumns().addAll(firstNameColumn, secondNameColumn, salaryColumn);

        ObservableList<EmployeeTableModel> data = FXCollections.observableArrayList();

        data.add(new EmployeeTableModel("Marek", "Kowalski", "6000"));
        data.add(new EmployeeTableModel("Anna", "Kot", "8000"));

        tableView.setItems(data);
    }
}
