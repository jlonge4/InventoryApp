package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** class modify product controller*/
/** RUNTIME ERROR I had the constructor for the new part out of order and couldn't understand why i was being asked to cast my double(price) to an int  */
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
    public Button toMainView;
    public Button saveModProduct;
    public Button partSearch;
    public TextField partsSearchText;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** initializes modify product screen and populates the selected product values into text fields*/
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
    /** exits to main view*/
    public void toMainView(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cancel to Main Screen");
        alert.setContentText("You are cancelling product modification");
        alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
    /** enables part search by button*/
    public void partSearch(ActionEvent actionEvent) {
        String searchText = partsSearchText.getText();
        if (searchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Failed");
            alert.setContentText("Enter a part name or ID number to search");
            alert.showAndWait();
        }
        ObservableList<Part> resultParts = Inventory.lookupPart(searchText);
        if (resultParts.size() == 0) {
            try {
                int partIDNumber = Integer.parseInt(searchText);
                Part p = Inventory.lookupPart(partIDNumber);
                if (p != null) {
                    resultParts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore exception
            }
        }
        PartsTable.setItems(resultParts);
        partsSearchText.setText("");
        if (resultParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed Search");
            alert.setContentText("No matching part found.");
            alert.showAndWait();
            PartsTable.setItems(Inventory.getAllParts());
        }

    }
    /** enables part search through text field*/
    public void partSearchText(KeyEvent actionEvent) {
        String searchText = partsSearchText.getText();
        ObservableList<Part> resultParts = Inventory.lookupPart(searchText);
        if (resultParts.size() == 0) {
            try {
                int partIDNumber = Integer.parseInt(searchText);
                Part p = Inventory.lookupPart(partIDNumber);
                if (p != null) {
                    resultParts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore exception
            }
        }
        PartsTable.setItems(resultParts);
        if (resultParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed Search");
            alert.setContentText("No matching part found.");
            alert.showAndWait();
            PartsTable.setItems(Inventory.getAllParts());
        }

    }
    /** adds associated part to modified product*/
    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        Product.addAssociatedPart(selectedAssociatedPart);
        PartsTable.setItems(Inventory.getAllParts());
    }
    /** deletes associated part to modified product*/
    public void onDeleteAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) prodAssociatedParts.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart != null) {
            Product.deleteAssociatedPart(selectedAssociatedPart);
       } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Selection Error");
           alert.setContentText("Please select a part to delete");
           alert.showAndWait();
       }
    }
    /** saves newly modified product*/
    @FXML
    void saveModProduct(ActionEvent actionEvent) throws Exception {
        int prodIndex = mainView.productToModifyIndex();
        int newProdID = Integer.parseInt(prodID.getText());
        String newProdName = prodName.getText();
        int newProdInv = Integer.parseInt(prodInv.getText());
        double newProdPrice = Double.parseDouble(prodPrice.getText());
        int newProdMin = Integer.parseInt(prodMin.getText());
        int newProdMax = Integer.parseInt(prodMax.getText());

        Product modifiedProduct = new Product (newProdID, newProdName, newProdPrice, newProdInv, newProdMin, newProdMax);
        for(Part p : associatedParts){
            modifiedProduct.addAssociatedPart(p);
        }
        if (isItemValid(modifiedProduct)) {
            Inventory.updateProduct(prodIndex, modifiedProduct);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Failed");
            alert.setContentText("invalid product");
            alert.showAndWait();
        }
        Parent modifyProductSave = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Scene scene = new Scene(modifyProductSave);
        Stage window = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    /** checks modified product is valid */
    public boolean isItemValid(Product product) throws IOException, NullPointerException, RuntimeException, NumberFormatException{
        if (product.getMin() > product.getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part min is greater than part max");
            alert.showAndWait();
            return false;
        }

        if (product.getStock() < product.getMin()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part inventory is less than part min");
            alert.showAndWait();
            return false;
        }
        if (product.getStock() > product.getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part inventory is greater than part max");
            alert.showAndWait();
            return false;
        }
        if (product.getPrice() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("part price cannot be less than zero");
            alert.showAndWait();
            return false;
        }
        if (product.getName().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            String partiDee = prodID.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            String newPartName = prodName.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartInv = Integer.parseInt(prodInv.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            double newPartPrice = Double.parseDouble(prodPrice.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartMin = Integer.parseInt(prodMin.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the min text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartMax = Integer.parseInt(prodMax.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the max text field");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}