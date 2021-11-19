package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class includes a constructor, getter, and setter methods for Products.*/
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Constructor for Products
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Setter Methods

    /** Id setter method.
     * @param id The id to be set.*/
    public void setId(int id) {
        this.id = id;
    }

    /** Name setter method.
     * @param name The name to be set.*/
    public void setName(String name) {
        this.name = name;
    }

    /** Price setter method.
     * @param price The price to be set.*/
    public void setPrice(double price) {
        this.price = price;
    }

    /** Stock setter method.
     * @param stock The stock/inventory to be set.*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Min setter method.
     * @param min The min to be set.*/
    public void setMin(int min) {
        this.min = min;
    }

    /** Max setter method.
     * @param max The max to be set.*/
    public void setMax(int max) {
        this.max = max;
    }

    //Getter Methods

    /** Id getter method.
     * @return The id.*/
    public int getId() {
        return id;
    }

    /** Name getter method.
     * @return The name.*/
    public String getName() {
        return name;
    }

    /** Price getter method.
     * @return The price.*/
    public double getPrice() {
        return price;
    }

    /** Stock getter method.
     * @return The stock/inventory.*/
    public int getStock() {
        return stock;
    }

    /** Min getter method.
     * @return The min.*/
    public int getMin() {
        return min;
    }

    /** Max getter method.
     * @return The max.*/
    public int getMax() {
        return max;
    }

    /** Adds a part to the associatedParts list.
     * @param part The part to be added.*/
    //Adds a part to the associatedParts list
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Deletes a part from the associateParts list if able to.
     * @param selectedAssociatedPart The part to be deleted.
     * @return True or false whether part can be deleted or not.*/
    //Deletes a selected part from the associatedParts list if able to
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(associatedParts.contains(selectedAssociatedPart))
        {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
        {
            return false;
        }
    }

    /** associatedParts getter method.
     * @return associatedParts list*/
    //Getter for associatedParts list
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
