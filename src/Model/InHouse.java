package Model;

/** The InHouse class inherits variables and methods from the Part class. An InHouse part uniquely has a machineId variable.*/
public class InHouse extends Part{

    private int machineId;

    //InHouse Constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** MachineId setter method.
     * @param machineId The machine Id to be set.*/
    //Setter Method
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** MachineId getter method.
     * @return The machine Id.*/
    //Getter Method
    public int getMachineId() {
        return machineId;
    }
}
