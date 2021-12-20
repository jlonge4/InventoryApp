package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addProductView implements Initializable {


    public TableView PartsTable;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn inventoryColumn;
    public TableColumn priceColumn;
    public TableView AssociatedPartsTable;
    public TableColumn partIDColumn;
    public TableColumn associatedNameColumn;
    public TableColumn aInventoryColumn;
    public TableColumn aPriceColumn;
    public Button toMainView;
    public TextField newProdID;
    public TextField newProdName;
    public TextField newProdInv;
    public TextField newProdPrice;
    public TextField newProdMin;
    public TextField newProdMax;

    private void addTestData() {
        InhousePart p = new InhousePart(1, "bolt", 3.00, 450, 0, 100, 01);
        Inventory.addPart(p);
        Product z = new Product(1, "saw", 300, 5, 10, 30);
        Inventory.addProduct(z);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("addProductView");

        PartsTable.setItems(Inventory.getAllParts());
        AssociatedPartsTable.setItems(Product.getAllAssociatedParts());


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addTestData();
    }

    public void toMainView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }

    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        Product.addAssociatedPart(selectedAssociatedPart);
    }

    public void onDeleteAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) AssociatedPartsTable.getSelectionModel().getSelectedItem();
        Product.deleteAssociatedPart(selectedAssociatedPart);
    }

    @FXML
    void addNewProductForm(ActionEvent event) throws Exception {

        int prodId = Integer.parseInt(newProdID.getText());
        String prodName = newProdName.getText();
        int prodInv = Integer.parseInt(newProdInv.getText());
        double prodPrice = Double.parseDouble(newProdPrice.getText());
        int prodMin = Integer.parseInt(newProdMin.getText());
        int prodMax = Integer.parseInt(newProdMax.getText());

        Product product = new Product(prodId, prodName, prodInv, (int) prodPrice, prodMin, prodMax);
        Inventory.addProduct(product);


        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
}



