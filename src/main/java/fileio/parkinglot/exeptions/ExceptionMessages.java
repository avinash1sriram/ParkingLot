package fileio.parkinglot.exeptions;

public enum ExceptionMessages {

    PARKING_ALREADY_EXIST("Sorry, parking already created"),
    PARKING_DOES_NOT_EXIST_ERROR("Sorry, vehicle parking does not exist"),
    INVALID_VALUE("{variable} value is incorrect"),
    NULL_OR_EMPTY("{variable} cannot be null or empty"),
    PROCESSING_ERROR("Processing error "),
    NO_VEHICLE_PARKED("No vehicle parked"),
    VEHICLE_ALREADY_PARKED("{vehicleRegistrationNumber} is already parked"),
    INVALID_COMMAND("Invalid command ");

    private String message = "";

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
