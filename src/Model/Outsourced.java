package Model;

/** The Outsourced class inherits variables and methods from the Part class. An Outsourced part uniquely has a companyName variable.*/
public class Outsourced extends Part {
    private String companyName;

    //Outsourced Constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** CompanyName setter method.
     * @param companyName The company name to be set.*/
    //Setter Method
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** CompanyName getter method.
     * @return The company name.*/
    //Getter Method
    public String getCompanyName() {
        return companyName;
    }

}
