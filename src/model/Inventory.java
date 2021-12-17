package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Inventory {

    private static ObservableList<InhousePart> inventoryTable = FXCollections.observableArrayList();

    public static void addPart(InhousePart inhousePart) {
        inventoryTable.add(inhousePart);
    }

    public static ObservableList<InhousePart> getInventory() {
        return inventoryTable;
    }
}
