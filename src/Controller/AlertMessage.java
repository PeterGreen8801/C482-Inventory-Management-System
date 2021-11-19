package Controller;

import javafx.scene.control.Alert;

/** The AlertMessage Controller class houses error messages and information that notify the user. It is composed of methods with switch statements that can be called to give a specific message to the user. The methods are made for the Main, Add Part, Modify Part, Add Product, and Modify Product Screens.*/
public class AlertMessage {

    /** Holds the cases for alerts on the Main Screen.*/
    //Handles alert messages for Main Screen
    public static void mainScreenAlert(int alertCode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        switch (alertCode) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Part not found.");
                alert.showAndWait();
                break;

            case 2:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Part not selected.");
                errorAlert.showAndWait();
                break;

            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Product not found.");
                alert.showAndWait();
                break;

            case 4:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Product not selected.");
                errorAlert.showAndWait();
                break;

            case 5:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Parts Associated");
                errorAlert.setContentText("All parts need to be removed from product before it is deleted.");
                errorAlert.showAndWait();
                break;
        }
    }

    /** Holds the cases for alerts on the Add Part Screen.*/
    //Handles alert messages for Add Part Screen
    public static void addPartAlert(int alertCode) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch(alertCode) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be less than Max and more than 0.");
                alert.showAndWait();
                break;

            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Machine ID field");
                alert.setContentText("Machine ID must contain numbers only.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Inventory field");
                alert.setContentText("Inventory must be between Min and Max or equal to them.");
                alert.showAndWait();
                break;

            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Empty Name");
                alert.setContentText("Name field cannot be blank.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Add Part Error");
                alert.setContentText("Invalid values or blank fields detected.");
                alert.showAndWait();
                break;
        }
    }

    /** Holds the cases for alerts on the Modify Part Screen.*/
    //Handles alert messages for Modify Part Screen
    public static void modifyPartAlert(int alertCode) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertCode) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be less than Max and more than 0.");
                alert.showAndWait();
                break;

            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Machine ID field");
                alert.setContentText("Machine ID must contain numbers only.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Inventory field");
                alert.setContentText("Inventory must be between Min and Max or equal to them.");
                alert.showAndWait();
                break;

            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Modify Part Error");
                alert.setContentText("Invalid values or blank fields detected.");
                alert.showAndWait();
                break;
        }
    }

    /** Holds the cases for alerts on the Add Product Screen.*/
    public static void addProductAlert(int alertCode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        switch(alertCode) {
            case 1:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid value for Min");
                errorAlert.setContentText("Min must be less than Max and more than 0.");
                errorAlert.showAndWait();
                break;
            case 2:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Part is not selected");
                errorAlert.showAndWait();
                break;
            case 3:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid Inventory field");
                errorAlert.setContentText("Inventory must be between Min and Max or equal to them.");
                errorAlert.showAndWait();
                break;
            case 4:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Empty Name");
                errorAlert.setContentText("Name field cannot be blank.");
                errorAlert.showAndWait();
                break;
            case 5:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Add Product Error");
                errorAlert.setContentText("Invalid values or blank fields detected.");
                errorAlert.showAndWait();
                break;
            case 6:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
        }
    }

    /** Holds the cases for alerts on the Modify Product Screen.*/
    public static void modifyProductAlert(int alertCode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        switch(alertCode) {
            case 1:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid value for Min");
                errorAlert.setContentText("Min must be less than Max and more than 0.");
                errorAlert.showAndWait();
                break;
            case 2:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Part is not selected");
                errorAlert.showAndWait();
                break;
            case 3:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Invalid Inventory field");
                errorAlert.setContentText("Inventory must be between Min and Max or equal to them.");
                errorAlert.showAndWait();
                break;
            case 4:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Empty Name");
                errorAlert.setContentText("Name field cannot be blank.");
                errorAlert.showAndWait();
                break;
            case 5:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Modify Product Error");
                errorAlert.setContentText("Invalid values or blank fields detected.");
                errorAlert.showAndWait();
                break;
            case 6:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
        }
    }

}
