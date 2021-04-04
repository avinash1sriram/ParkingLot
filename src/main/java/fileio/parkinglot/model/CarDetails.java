package fileio.parkinglot.model;

import fileio.parkinglot.exeptions.ExceptionMessages;
import fileio.parkinglot.exeptions.ParkingLotException;

public class CarDetails extends VehicleDetails {

    public CarDetails(String registrationNumber) throws ParkingLotException {
        super(registrationNumber,"Car");
    }

    @Override
    protected void validateVehicleRegistrationNumber(String registrationNumber) throws ParkingLotException {

        if(!registrationNumber.matches("^[A-Z]{2}[-][0-9]{2}[-][A-Z]{2}[-][0-9]{4}$")){

            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "registrationNumber"));

        }

    }
}
