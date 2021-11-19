package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Inventory class houses the lists of parts/products and methods to manipulate them. The class also includes methods to generate unique ids for parts and products.*/
public class Inventory {
    //Creates all Parts and Products lists to be used in MainScreen
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int uniquePartId = 1;
    private static int uniqueProductId = 1;

    /** Adds a part to the allParts list if it is not null.
     * @param partToBeAdded The part that will be added to the list.*/
    //Allows part to be added to allParts list if not null
    public static void addPart(Part partToBeAdded) {
        if (partToBeAdded != null) {
            allParts.add(partToBeAdded);
        }
    }

    /** Adds a product to the allProducts list if not null.
     * @param productToBeAdded The product that will be added to the list.*/
    //Allows product to be added to allProducts list if not null
    public static void addProduct(Product productToBeAdded) {
        if(productToBeAdded != null) {
            allProducts.add(productToBeAdded);
        }
    }

    /** Returns a specific part from a given part Id if there is one.
     * @param partId The part Id being compared to.
     * @return A matching part or null if there is not one.*/
    //Looks up specific part from id
    public static Part lookupPart(int partId) {
        Part foundPart = null;

        for(Part part : allParts) {
            if(part.getId() == partId) {
                foundPart = part;
            }
        }

        return foundPart;
    }

    /** Returns a specific product from a given product Id if there is one.
     * @param productId The product Id being compared to.
     * @return A matching product or null if there is not one.*/
    //Looks up specific product from id
    public static Product lookupProduct(int productId) {
        Product foundProduct = null;

        for(Product product :allProducts) {
            if(product.getId() == productId) {
                foundProduct = product;
            }
        }

        return foundProduct;
    }

    /** Returns a list of parts that match the given part name.
     * @param partName The part name being compared to.
     * @return A list of Parts that match the part name being searched.*/
    //Adds found part(s) to foundParts list for searching
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for(Part part : allParts) {
            if(part.getName().equals(partName)) {
                foundParts.add(part);
            }
        }

        return foundParts;
    }
    /** Returns a list of products that match the given product name.
     * @param productName The product name being compared to.
     * @return A list of Products that match the product name being searched. */
    //Adds found product(s) to foundProducts list for searching
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for(Product product : allProducts) {
            if(product.getName().equals(productName)) {
                foundProducts.add(product);
            }
        }

        return foundProducts;
    }

    /** Updates a part with a specified index.
     * @param index Index to be set.
     * @param selectedPart The selected part to be updated.*/
    //Update specific Part
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Updates a product with a specified index.
     * @param index Index to be set.
     * @param selectedProduct The selected product to be updated.*/
    //Update specific Product
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** Deletes a part that is selected if able to.
     * @param selectedPart The part to be deleted.
     * @return True or false whether the part exists and can be deleted.*/
    //Deletes a selected Part if able to
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Deletes a product that is selected if able to.
     * @param selectedProduct The product to be deleted.
     * @return True or false whether the part exists and can be deleted.*/
    //Deletes a selected Product if able to
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** AllParts getter method.
     * @return List of all parts.*/
    //Getter for allParts
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** AllProducts getter method.
     * @return List of all products.*/
    //Getter for allProducts
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Creates a new part Id. Adds the size of the list plus the uniquePartId(which is 1).
     * @return A new int that will be used as a new part Id.*/
    //Method for generating a new part id
    public static int generatePartId() {
        return  allParts.size() + uniquePartId;
    }

    /** Creates a new product Id. Adds the size of the list plus the uniqueProductId(which is 1).
     * @return A new int that will be used as a new product Id.*/
    //Method for generating a new product id
    public static int generateProductId() {
        return allProducts.size() + uniqueProductId;
    }
}
