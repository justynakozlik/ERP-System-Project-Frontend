package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dto.EmployeeDto;
import sample.rest.EmployeeRestClient;
import sample.table.EmployeeTableModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppController implements Initializable {

    private final EmployeeRestClient employeeRestClient;

    public AppController(){
        employeeRestClient = new EmployeeRestClient();
    }

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

        loadEmployeeData(data);

        tableView.setItems(data);
    }

    private void loadEmployeeData(ObservableList<EmployeeTableModel> data) {

        Thread thread = new Thread(() -> {

            List<EmployeeDto> employees = employeeRestClient.getEmployees();
            data.addAll(employees.stream().map(EmployeeTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }
}
