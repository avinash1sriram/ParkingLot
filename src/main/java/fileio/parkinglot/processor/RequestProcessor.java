package fileio.parkinglot.processor;

import fileio.parkinglot.exeptions.ExceptionMessages;
import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.model.CarDetails;
import fileio.parkinglot.model.DriverDetails;
import fileio.parkinglot.model.VehicleDetails;
import fileio.parkinglot.service.ParkingService;
import fileio.parkinglot.utils.StringUtils;

import static fileio.parkinglot.utils.ParkingCommandConstants.*;

public class RequestProcessor {

    private ParkingService parkingService;

    public RequestProcessor(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public String execute(String input) throws ParkingLotException {

        if (StringUtils.isEmpty(input))
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "input line"));

        //Splitting the input string by space to get the command and arguments
        String[] inputArguments = input.split(" ");

        //first word will be command as per the input format
        String command = inputArguments[0];
        String output;

        switch (command) {

            case CREATE_PARKING_LOT:
                output = parkingService.createParkingLot(Integer.parseInt(inputArguments[1]));
                break;

            case PARK:
                VehicleDetails vehicleDetails = new CarDetails(inputArguments[1]);
                DriverDetails driverDetails = new DriverDetails(Long.valueOf(inputArguments[3]));
                output = parkingService.parkVehicle(vehicleDetails, driverDetails);
                break;

            case LEAVE:
                output = parkingService.leaveVehicle(Long.valueOf(inputArguments[1]));
                break;

            case VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE:
                output = parkingService.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(Long.valueOf(inputArguments[1]));
                break;

            case SLOT_NUMBERS_FOR_DRIVER_OF_AGE:
                output = parkingService.getSlotNumberWhichAreDrivenByDriversOfParticularAge(Long.valueOf(inputArguments[1]));
                break;

            case SLOT_NUMBER_FOR_CAR_WITH_NUMBER:
                output = parkingService.getSlotNumberGivenVehicleRegistrationNumber(inputArguments[1]);
                break;

            default:
                throw new ParkingLotException(ExceptionMessages.INVALID_COMMAND.getMessage());
        }

        return output;
    }

}
