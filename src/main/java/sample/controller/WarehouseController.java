package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.dto.ItemDto;
import sample.dto.WarehouseDto;
import sample.dto.WarehouseModuleDto;
import sample.rest.ItemRestClient;
import sample.rest.WarehouseRestClient;
import sample.table.ItemTableModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WarehouseController implements Initializable {

    @FXML
    public ComboBox<WarehouseDto> warehouseComboBox;
    @FXML
    private BorderPane warehouseBorderPane;
    @FXML
    private Button addButton;
    @FXML
    private Button viewButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TableView<ItemTableModel> tableView;

    private final ItemRestClient itemRestClient;

    private final WarehouseRestClient warehouseRestClient;

    private ObservableList<ItemTableModel> data;

    public WarehouseController() {
        itemRestClient = new ItemRestClient();
        data = FXCollections.observableArrayList();
        warehouseRestClient = new WarehouseRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
        initializeComboBox();
    }

    private void initializeComboBox() {
        warehouseComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            if (!newValue.equals(oldValue) && oldValue != null) {
                WarehouseDto warehouseDto = warehouseComboBox.getSelectionModel().getSelectedItem();
                loadItemData(warehouseDto);
            }
        }));
    }

    private void initializeTableView() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("name"));

        TableColumn quantityColumn = new TableColumn("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, Double>("quantity"));

        TableColumn quantityTypeColumn = new TableColumn("Quantity Type");
        quantityTypeColumn.setMinWidth(100);
        quantityTypeColumn.setCellValueFactory(new PropertyValueFactory<ItemTableModel, String>("quantityType"));

        tableView.getColumns().addAll(nameColumn, quantityColumn, quantityTypeColumn);

        tableView.setItems(data);

        loadItemData();
    }

    private void loadItemData() {
        Thread thread = new Thread(() -> {
            WarehouseModuleDto warehouseModuleDto = warehouseRestClient.getWarehouseModuleData();
            data.clear();
            setWarehouseComboBoxItems(warehouseModuleDto);
            data.addAll(warehouseModuleDto.getItemDtoList().stream().map(ItemTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private void loadItemData(WarehouseDto warehouseDto) {
        Thread thread = new Thread(() -> {
            WarehouseModuleDto warehouseModuleDto = warehouseRestClient.getWarehouseModuleData(warehouseDto.getIdWarehouse());
            data.clear();
            setWarehouseComboBoxItems(warehouseModuleDto);
            data.addAll(warehouseModuleDto.getItemDtoList().stream().map(ItemTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private void setWarehouseComboBoxItems(WarehouseModuleDto warehouseModuleDto) {
        List<WarehouseDto> warehouseDtoList = warehouseModuleDto.getWarehouseDtoList();
        ObservableList<WarehouseDto> warehouseComboBoxItems = FXCollections.observableArrayList();
        warehouseComboBoxItems.addAll(warehouseDtoList);
        Platform.runLater(() -> {
            warehouseComboBox.setItems(warehouseComboBoxItems);
            warehouseComboBox.getSelectionModel().select(warehouseDtoList.indexOf(warehouseModuleDto.getSelectedWarehouse()));
        });
    }
}
