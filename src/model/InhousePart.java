package model;
/** Inhouse part class instanc extends provided part class*/
/** RUNTIME ERROR I had the constructor for the new part out of order and couldn't understand why i was being asked to cast my double(price) to an int  */
public class InhousePart extends Part {

    private int machineID;
    /** inhouse part constructor*/
    public InhousePart (int id, String name, double price, int stock, int min, int max, int machineID) {
            super(id, name, price, stock, min, max);
            this.machineID = machineID;
    }
    /** gets inhouse part instance machine id*/
    public int getMachineID() {
        return machineID;
    }
    /** sets inhouse part instance machine id*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
