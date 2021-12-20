package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InhousePart;
import model.Inventory;
import model.Part;
import model.Product;

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
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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
}
