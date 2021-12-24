package controller;


import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** mainView class that is the main view controller */
/** FUTURE ENHANCEMENT In the future, a table that allows you to see the inhouse
 * or outsourced radio selection would be cool.  */
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
    public TextField partsSearchText;
    public Button partSearch;
    public Button productSearch;
    public TextField productSearchText;
    private static int modifyPartIndex;
    private static Part modifyPart = null;
    private static Product modifyProduct = null;
    private static int modifyProductIndex;
    /** gets part to be modified from table */
    public static Part getPart() {
        return modifyPart;
    }
    /** gets product to be modified from table */
    public static Product getProduct() {
        return modifyProduct;
    }
    /** gets Part to be modified index */
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }
    /** gets product to be modified index */
    public static int productToModifyIndex() {
        return modifyProductIndex;
    }

    /** initializes mainveiw screen */
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


    }
    /** initializes the add product screen*/
    public void toAddProductView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProductView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("addProductView");
        stage.setScene(scene);
        stage.show();
    }
    /** initializes the modify product screen*/
    public void toModifyProductView(ActionEvent actionEvent) throws IOException {
        try {
            modifyProduct = (Product) ProductsTable.getSelectionModel().getSelectedItem();
            modifyProductIndex = Inventory.getAllProducts().indexOf(modifyProduct);
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProductView.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("modifyProductView");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a product to Modify");
            alert.showAndWait();
        }
    }
    /** initializes the add part screen*/
    public void toAddPartFirstView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPartFirstView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 325, 400);
        stage.setTitle("addPartFirstView");
        stage.setScene(scene);
        stage.show();
    }
    /** initializes the modify part screen*/
    public void toModifyPartFirstView(ActionEvent event) throws IOException {
        try {
            modifyPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
            modifyPartIndex = Inventory.getAllParts().indexOf(modifyPart);
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("/view/modifyPartFirstView.fxml"));
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage modifyPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();
        } catch (Exception c) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
    }
    /** exits the program*/
    public void exitProgram(ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }
    /** deletes selected part*/
    public void onDeletePart(ActionEvent actionEvent) {
        try {
            Part selectedPart = (Part) PartsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
            PartsTable.setItems(Inventory.getAllParts());
        }  catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }
    /** deletes selected product*/
    public void onDeleteProduct(ActionEvent actionEvent) {
        try {
            Product selectedProduct = (Product) ProductsTable.getSelectionModel().getSelectedItem();
            if (selectedProduct.getAllAssociatedParts().size() != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Associated Part Error");
                alert.setContentText("Please remove associated parts");
                alert.showAndWait();
            } else {
                Inventory.deleteProduct(selectedProduct);
                ProductsTable.setItems(Inventory.getAllProducts());
            }
        }  catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        }

    }
    /** conducts part search*/
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
    /** conducts part search*/
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
    /** conducts product search*/
    public void productSearch(ActionEvent actionEvent) {
        String searchText = productSearchText.getText();
        if (searchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed Search");
            alert.setContentText("Enter the product name or ID number to search");
            alert.showAndWait();
        }
        ObservableList<Product> resultProducts = Inventory.lookupProduct(searchText);
        if (resultProducts.size() == 0) {
            try {
                int productID = Integer.parseInt(searchText);
                Product product = Inventory.lookupProduct(productID);
                if (product != null) {
                    resultProducts.add(product);
                }
            } catch (NumberFormatException ignored) {

            }
        }
        ProductsTable.setItems(resultProducts);
        productSearchText.setText("");
        if (resultProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Failed");
            alert.setContentText("No matching Products found.");
            alert.showAndWait();
            ProductsTable.setItems(Inventory.getAllProducts());
        }
    }
    /** conducts product search*/
    public void productSearchText(KeyEvent actionEvent) {
        String searchText = productSearchText.getText();
        ObservableList<Product> resultProducts = Inventory.lookupProduct(searchText);
        if (resultProducts.size() == 0) {
            try {
                int prodIDNumber = Integer.parseInt(searchText);
                Product prod = Inventory.lookupProduct(prodIDNumber);
                if (prod != null) {
                    resultProducts.add(prod);
                }
            } catch (NumberFormatException e) {
                //ignore exception
            }
        }
        ProductsTable.setItems(resultProducts);
        if (resultProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed Search");
            alert.setContentText("No matching part found.");
            alert.showAndWait();
            ProductsTable.setItems(Inventory.getAllProducts());
        }

    }
    /** makes sure items to be added or modified are valid*/
    public static void isItemValid(Part part) throws IOException, NullPointerException, RuntimeException, NumberFormatException{
            if (part.getMin() > part.getMax()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Failed");
                alert.setContentText("part min is greater than part max");
                alert.showAndWait();
            }

            if (part.getStock() < part.getMin()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Failed");
                alert.setContentText("part inventory is less than part min");
                alert.showAndWait();
            }
            if (part.getStock() > part.getMax()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Failed");
                alert.setContentText("part inventory is greater than part max");
                alert.showAndWait();
            }
            if (part.getPrice() < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Failed");
                alert.setContentText("part price cannot be less than zero");
                alert.showAndWait();
            }
            if (part.getName().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Failed");
                alert.setContentText("please fill in the empty text field");
                alert.showAndWait();
            } else {
                try {
                    Inventory.addPart(part);

                } catch (NumberFormatException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Failed");
                    alert.setContentText("Please fill in missing or incorrect text field");
                    alert.showAndWait();
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Failed");
                    alert.setContentText("Please fill in missing or incorrect text field");
                    alert.showAndWait();
                }
            }
    }
}
