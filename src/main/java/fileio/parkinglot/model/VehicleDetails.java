package fileio.parkinglot.model;

// This model will contain the common properties of the vehicles
public abstract class VehicleDetails {

    // This will be used to store the vehicle registration number
    private String registrationNumber = null;

    // This type will indicate whether it car/bike etc.,
    private String type = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VehicleDetails(String registrationNumber,
                          String type) {
        this.registrationNumber = registrationNumber;
        this.type = type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }


}
