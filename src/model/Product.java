package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product class instance*/
/** RUNTIME ERROR attempted to make a selected Product equal to the associatedProducts List  */
public class Product  {

        private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        private int id;
        private String name;
        private double price;
        private int stock;
        private int min;
        private int max;
    /** returns all associated parts */
    public static ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    /** associated parts to set */
    public static void setAssociatedParts(ObservableList<Part> associatedParts) {
        Product.associatedParts = associatedParts;
    }
    /** product constructor */
    public Product(int id, String name, double price, int stock, int min, int max) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.min = min;
            this.max = max;

        }
    /** returns product id */
        public int getId() {
            return id;
        }

    /** sets product id */
        public void setId(int id) {
            this.id = id;
        }

    /** returns product name */
        public String getName() {
            return name;
        }

    /** sets product name */
        public void setName(String name) {
            this.name = name;
        }

    /** returns product price */
        public double getPrice() {
            return price;
        }

    /** sets product price */
        public void setPrice(double price) {
            this.price = price;
        }

    /** returns product inventory */
        public int getStock() {
            return stock;
        }

    /** sets product inventory*/
        public void setStock(int stock) {
            this.stock = stock;
        }

    /** returns product min */
        public int getMin() {
            return min;
        }

    /** sets product min */
        public void setMin(int min) {
            this.min = min;
        }

    /** returns product max */
        public int getMax() {
            return max;
        }

    /** sets product max */
        public void setMax(int max) {
            this.max = max;
        }
    /** adds associated part to the product*/
        public static void addAssociatedPart(Part part) {
            if (part != null) {
                Product.associatedParts.add(part);
            }

        }
    /** deletes associated part from current product */
        public static boolean deleteAssociatedPart(Part selectedAssociatedPart) {
            if (selectedAssociatedPart != null) {
                Product.associatedParts.remove(selectedAssociatedPart);
                return true;
            }
            return false;
        }
    /** returns all associated parts for current product*/
        public static ObservableList getAllAssociatedParts() {
            return Product.associatedParts;
        }
}
