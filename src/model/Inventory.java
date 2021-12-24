package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
/** new class inventory that is responsible for handling parts and products ***/
/** FUTURE ENHANCEMENT make a table view for each observable list on one page   */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static TableView<Object> PartsTable;

    /** adds parts to partslist ***/
    public static void addPart(Part Part) {
        allParts.add(Part);
    }
    /** adds products to product list ***/
    public static void addProduct(Product Product) {
        allProducts.add(Product);
    }
    /** looks up part by key letter or letters and returns matching parts ***/
    public static ObservableList<Part> lookupPart(String keyChar){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for(Part p : allParts){
            if(p.getName().toLowerCase().contains(keyChar.toLowerCase())){
                foundParts.add(p);
            }
        }
        return foundParts;
    }
    /** looks up part by key ID and return parts ***/
    public static Part lookupPart(int searchedIDNumber){
        ObservableList<Part> partsToSearch = Inventory.getAllParts();
        for(int i = 0; i < partsToSearch.size(); i++){
            Part p = partsToSearch.get(i);
            if(p.getId() == searchedIDNumber){
                return p;
            }
        }
        return null;
    }
    /** looks up product by key ID and return products ***/
    public static Product lookupProduct(int productID){
        ObservableList<Product> productsToSearch = Inventory.getAllProducts();
        for(Product product : productsToSearch) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }
    /** looks up product by key letter or letters and returns matching products ***/
    public static ObservableList<Product> lookupProduct(String searchedProduct){
        ObservableList<Product> matchedProducts = FXCollections.observableArrayList();
        for(Product prod : allProducts){
            if(prod.getName().toLowerCase().contains(searchedProduct.toLowerCase())){
                matchedProducts.add(prod);
            }
        }
        return matchedProducts;
    }
    /** updates parts by matching index when they are modified*/
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    /** updates products by matching index when they are modified*/
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);

    }
    /** if a part is selected to be deleted, it will delete the selected part*/
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart != null) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }
    /** if a product is selected to be deleted, it will delete the selected product*/
    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct != null) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }
    /** returns all currently created parts*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** returns all currently created products*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
