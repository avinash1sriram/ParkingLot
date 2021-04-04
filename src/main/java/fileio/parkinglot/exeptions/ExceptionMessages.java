package fileio.parkinglot.exeptions;

public enum ExceptionMessages {

    PARKING_ALREADY_EXIST("Sorry, Parking Already Created"),
    PARKING_NOT_EXIST_ERROR("Sorry, Vehicle Parking Does not Exist"),
    INVALID_VALUE("{variable} value is incorrect"),
    NULL_OR_EMPTY("{variable} cannot be null or empty"),
    PROCESSING_ERROR("Processing Error "),
    NO_VEHICLE_PARKED("No vehicle parked"),
    INVALID_COMMAND("Invalid command to execute");

    private String message = "";


    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
