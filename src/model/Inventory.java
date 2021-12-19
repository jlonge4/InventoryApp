package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part Part) {
        allParts.add(Part);
    }

    public static void addProduct(Product Product) {
        allProducts.add(Product);
    }

    public static Part lookupPart(int ID) {
//      iterate through allParts. to return by ID
        return p;
    }


    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
