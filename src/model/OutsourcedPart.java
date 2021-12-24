package model;
/** Outsourced part class instanc extends provided part class*/
/** RUNTIME ERROR I had the constructor for the new part out of order and couldn't understand why i was being asked to cast my double(price) to an int  */
public class OutsourcedPart extends Part {

    private String companyName;
    /** outsourced part constructor*/
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** gets outsourced part instance company name*/
    public String getCompanyName() {
        return companyName;
    }
    /** sets outsourced part instance company name*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
