package model;

public class InhousePart extends Part {
    private boolean inhouse;
    private int machineID;

    public InhousePart (int id, String name, double price, int stock, int min, int max) {
            super(id, name, price, stock, min, max);
//            this.inhouse = inhousePart;
    }

    public boolean getInhouse() {
        return inhouse;
    }
}
