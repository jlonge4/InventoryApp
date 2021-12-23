package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import model.Inventory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InhousePart;
import model.Part;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainView implements Initializable {

    public TableView PartsTable;
    public TableColumn idColumn;
    public TableColumn partNameColumn;
    public TableColumn inventoryColumn;
    public TableColumn priceColumn;
    public TableColumn prodIDcol;
    public TableColumn prodNameCol;
    public TableColumn prodInvCol;
    public TableColumn prodPriceCol;
    public TableView ProductsTable;
    public TextField partsSearch;
    public Button partSearch;

    private static int modifyPartIndex;
    private static Part modifyPart = null;
    private static Product modifyProduct = null;
    private static int modifyProductIndex;


    public static Part getPart() {
        return modifyPart;
    }

    public static Product getProduct() {
        return modifyProduct;
    }

    public static int partToModifyIndex() {
        return modifyPartIndex;
    }

    private void addTestData() {
        InhousePart p = new InhousePart(1, "bolt", 3.00, 450, 0, 100, 01);
        Inventory.addPart(p);
        Product z = new Product(1, "saw", 300, 5, 10, 30);
        Inventory.addProduct(z);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

        PartsTable.setItems(Inventory.getAllParts());
        ProductsTable.setItems(Inventory.getAllProducts());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addTestData();
    }

    public void toAddProductView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProductView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("addProductView");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProductView(ActionEvent actionEvent) throws IOException {
        modifyProduct = (Product) ProductsTable.getSelectionModel().getSelectedItem();
        modifyProductIndex = Inventory.getAllParts().indexOf(modifyProduct);
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProductView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("modifyProductView");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddPartFirstView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPartFirstView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 325, 400);
        stage.setTitle("addPartFirstView");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyPartFirstView(ActionEvent event) throws IOException {
        modifyPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        modifyPartIndex = Inventory.getAllParts().indexOf(modifyPart);
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("/view/modifyPartFirstView.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }

    public void exitProgram(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }

    public void onDeletePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
    }

    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = (Product) ProductsTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
    }

    public void partSearch(ActionEvent actionEvent) {
            if (isInt(partsSearch.getText())) {
                int searchField = Integer.parseInt(partsSearch.getText());
                int partIndex = -1;
                if (Inventory.lookupPart(searchField) == -1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Error");
                    alert.setHeaderText("Part not found");
                    alert.setContentText("The search term entered does not match any known parts.");
                    alert.showAndWait();
                } else {
                    partIndex = Inventory.lookupPart(searchField);
                    Part tempPart = Inventory.getAllParts().get(partIndex);
                    ObservableList<Part> tempPartList = FXCollections.observableArrayList();
                    tempPartList.add(tempPart);
                    PartsTable.setItems(tempPartList);
                }
            } else {
//            part = Inventory.lookupPart(searchField);
//            Part tempPart = Inventory.getAllParts().get(partIndex);
//            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
//            tempPartList.add(tempPart);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Part not found");
                alert.setContentText("The search term entered does not match any known parts.");
                alert.showAndWait();

//                PartsTable.setItems(Inventory.lookupParts(partsSearch.getText()));
            }
        }

    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    public void partsSearch(ActionEvent actionEvent) {
        PartsTable.setItems(Inventory.lookupParts(partsSearch.getText()));
    }
}
