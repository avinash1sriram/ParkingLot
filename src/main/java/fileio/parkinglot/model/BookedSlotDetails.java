package fileio.parkinglot.model;

// This entity/model will hold the details of entire parked vehicle
public class BookedSlotDetails {

    private Slot slot;
    private VehicleDetails vehicleDetails;
    private DriverDetails driverDetails;

    public BookedSlotDetails(Slot slot, VehicleDetails vehicleDetails, DriverDetails driverDetails) {
        this.slot = slot;
        this.vehicleDetails = vehicleDetails;
        this.driverDetails = driverDetails;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetails driverDetails) {
        this.driverDetails = driverDetails;
    }

}
