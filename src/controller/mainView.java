package controller;

import com.sun.javafx.charts.Legend;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
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
import org.w3c.dom.ls.LSOutput;

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

    public void toModifyPartFirstView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPartFirstView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 325, 400);
        stage.setTitle("modifyPartFirstView");
        stage.setScene(scene);
        stage.show();
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


}
