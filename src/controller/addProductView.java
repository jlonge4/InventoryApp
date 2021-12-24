package controller;

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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
/** new class (controller) for adding products ***/
/** RUNTIME ERROR I had the constructor for the new part out of order and couldn't understand why i was being asked to cast my double(price) to an int  */
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
    public Button partSearch;
    public TextField partsSearchText;
    public Button toMain;

    /* increases count each time it is called for random ID***/
    public int increaseCount(int count) {
        count ++;
        return count;
    }
    /* generates a random ID ***/
    public int randomId() {
        AtomicInteger randomId = new AtomicInteger(increaseCount(Inventory.getAllProducts().size()));
        Inventory.getAllProducts().forEach((item) -> {
            if (item.getId() == randomId.get()) {
                randomId.addAndGet(1);
            };

        });
        return randomId.get();
    }
    /* initializes the AddProductScreen ***/
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

    }
    /* returns to main screen upon cancel***/
    public void toMainView(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cancel to Main Screen");
        alert.setContentText("You are cancelling product add");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) toMain.getScene().getWindow();
                stage.close();
            }
        });
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
    /* allows to search for parts ***/
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
    /* allows to search for parts ***/
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
    /* adds associated part to parts table ***/
    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
        Product.addAssociatedPart(selectedAssociatedPart);
        PartsTable.setItems(Inventory.getAllParts());
    }
    /* removes associated parts from associatedParts table ***/
    public void onDeleteAssociatedPart(ActionEvent actionEvent) {
        Part selectedAssociatedPart = (Part) AssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart != null) {
            Product.deleteAssociatedPart(selectedAssociatedPart);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }
    /* allows creation of a new product and takes in text field values to generate new product ***/
    @FXML
    void addNewProductForm(ActionEvent event) throws Exception {

        int prodId = randomId();
        String prodName = newProdName.getText();
        int prodInv = Integer.parseInt(newProdInv.getText());
        double prodPrice = Double.parseDouble(newProdPrice.getText());
        int prodMin = Integer.parseInt(newProdMin.getText());
        int prodMax = Integer.parseInt(newProdMax.getText());

        Product product = new Product(prodId, prodName, prodPrice, prodInv, prodMin, prodMax);
        Part selectedAssociatedPart = (Part) AssociatedPartsTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(selectedAssociatedPart);
        if (isItemValid(product)) {
            Inventory.addProduct(product);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("Invalid product");
            alert.showAndWait();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("mainView");
        stage.setScene(scene);
        stage.show();
    }
    /* detects if new product is valid ***/
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
            String partiDee = newProdID.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in the empty text field");
            alert.showAndWait();
            return false;
        }
        try {
            String newPartName = newProdName.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the name text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartInv = Integer.parseInt(newProdInv.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the Inv text field");
            alert.showAndWait();
            return false;
        }
        try {
            double newPartPrice = Double.parseDouble(newProdPrice.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the price text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartMin = Integer.parseInt(newProdMin.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the min text field");
            alert.showAndWait();
            return false;
        }
        try {
            int newPartMax = Integer.parseInt(newProdMax.getText());
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



