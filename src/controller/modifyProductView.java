package controller;

import javafx.event.ActionEvent;
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
import model.Inventory;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyProductView implements Initializable {

    public TableView PartsTable;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn inventoryColumn;
    public TableColumn priceColumn;
    public TableView prodAssociatedParts;
    public TextField prodMin;
    public TextField prodMax;
    public TextField prodPrice;
    public TextField prodInv;
    public TextField prodName;
    public TextField prodID;
    public TableColumn priceColumnAP;
    public TableColumn invColumnAP;
    public TableColumn nameColumnAP;
    public TableColumn idColumnAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartsTable.setItems(Inventory.getAllParts());


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        System.out.println("modifyProductView");

        Product product = mainView.getProduct();
        prodID.setText(Integer.toString(product.getId()));
        prodName.setText(product.getName());
        prodPrice.setText(Double.toString(product.getPrice()));
        prodMax.setText(Integer.toString(product.getMax()));
        prodMin.setText(Integer.toString(product.getMin()));
        prodInv.setText(Integer.toString(product.getStock()));
        prodAssociatedParts.setItems(product.getAllAssociatedParts());

        idColumnAP.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnAP.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumnAP.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnAP.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
    public void toMainView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
}