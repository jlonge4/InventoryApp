package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static TableView<Object> PartsTable;


    public static void addPart(Part Part) {
        allParts.add(Part);
    }

    public static void addProduct(Product Product) {
        allProducts.add(Product);
    }

//    public static Part lookupPart(int ID) {
////      iterate through allParts. to return by ID
//        return p;
//    }

    //    public static Product lookupProduct(int ID) {
////      iterate through allProducts. to return by ID
//        return p;
//    }
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart != null) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct != null) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
