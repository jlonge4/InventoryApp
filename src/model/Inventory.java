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

    public static ObservableList<Part> lookupParts(String partName) {
        ObservableList<Part> searchPartList = FXCollections.observableArrayList();
        do {
            for (int i = 0; i < allParts.size(); i++) {
                partName = partName.toLowerCase();
                if (partName.equals(allParts.get(i).getName().toLowerCase())) {
                    Part searched = allParts.get(i);
                    searchPartList.add(searched);

                }
                return searchPartList;
            }
            return allParts;
        } while (partName.length() > 0);
    }



    public static int lookupPart(int partId) {
       boolean isFound = false;
        int index = 0;
            for (int i = 0; i < allParts.size(); i++) {
                if (partId == allParts.get(i).getId()) {
                    index = i;
                    isFound = true;
                }
            }
            if (isFound == true) {
                return index;
            }
            else {
                System.out.println("No parts found.");
                return -1;
            }

    }

    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public static void updateProduct(int index, Product selectedProduct) {
//        do some stuff

    }
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
