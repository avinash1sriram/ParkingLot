package fileio.parkinglot.service.impl;

import fileio.parkinglot.dao.ParkingLotDataManager;
import fileio.parkinglot.dao.impl.ParkingLotDataManagerImpl;
import fileio.parkinglot.exeptions.ExceptionMessages;
import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.model.BookedSlotDetails;
import fileio.parkinglot.model.DriverDetails;
import fileio.parkinglot.model.VehicleDetails;
import fileio.parkinglot.parkingstrategies.NearestFirstParkingStrategy;
import fileio.parkinglot.service.ParkingService;
import fileio.parkinglot.utils.ObjectUtils;
import fileio.parkinglot.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ParkingServiceImpl implements ParkingService {

    private ParkingLotDataManager<VehicleDetails> parkingLotDataManager = null;

    public ParkingLotDataManager<VehicleDetails> getParkingLotDataManager() {
        return parkingLotDataManager;
    }

    /*
    *This method is responsible for creating or initialising the parking lot
    *  */
    @Override
    public String createParkingLot(int capacity) throws ParkingLotException {

        if (capacity < 0 || capacity > 1000) {
            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
        }

        if(ObjectUtils.isNotEmpty(parkingLotDataManager)){
            throw new ParkingLotException(ExceptionMessages.PARKING_ALREADY_EXIST.getMessage());
        }

        parkingLotDataManager = ParkingLotDataManagerImpl.intialiseAndCreateParkingLot(capacity, new NearestFirstParkingStrategy());
        return "Created parking of " + capacity + "slots";
    }

    /*
    * This method is responsible for parking a vehicle.
    * If the parking space does not exists it will throw error saying that parking space not present
    * */

    @Override
    public String parkVehicle(VehicleDetails vehicleDetails, DriverDetails driverDetails) throws ParkingLotException {

        if (ObjectUtils.isEmpty(vehicleDetails)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "vehicleDetails"));
        }
        if (ObjectUtils.isEmpty(driverDetails)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "driverDetails"));
        }

        BookedSlotDetails bookedSlotDetails = parkingLotDataManager.parkVehicle(vehicleDetails, driverDetails);

        if (ObjectUtils.isEmpty(bookedSlotDetails)) {

            throw new ParkingLotException(ExceptionMessages.PARKING_DOES_NOT_EXIST_ERROR.getMessage());

        }

        return bookedSlotDetails.getVehicleDetails().getType() + " with vehicle registration Number " + bookedSlotDetails.getVehicleDetails().getRegistrationNumber() + " has been parked at slot number " + bookedSlotDetails.getSlot().getId();
    }


    /*
    * This method is responsible for leaving the vehicle from the parking slot
    * If the vehicle does not exist, it will throw the error
    * */
    @Override
    public String leaveVehicle(Long slotId) throws ParkingLotException {

        if (ObjectUtils.isEmpty(slotId)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "slotId"));
        }

        if (slotId < 0L || slotId > 1000L) {
            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "slotId"));
        }

        BookedSlotDetails bookedSlotDetails = parkingLotDataManager.leaveVehicle(slotId);

        if (ObjectUtils.isEmpty(bookedSlotDetails)) {

            throw new ParkingLotException(ExceptionMessages.NO_VEHICLE_PARKED.getMessage());

        }

        return "Slot number " + bookedSlotDetails.getSlot().getId() + " vacated the " + bookedSlotDetails.getVehicleDetails().getType() + " with registration number " + bookedSlotDetails.getVehicleDetails().getRegistrationNumber() + " left the space, the driver of the car was of the age " + bookedSlotDetails.getDriverDetails().getAge();

    }

    /*
     * This method is responsible for fetching the vehicle numbers that are parked by driver of particular age
     * If there are many vehicles, the list of them will be returned seperated by a ","
     * */
    @Override
    public String getVehicleNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException {

        if (ObjectUtils.isEmpty(age)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "age"));
        }

        if (age < 0L || age > 1000L) {
            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "age"));
        }

        List<String> vehicleNumbers = parkingLotDataManager.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(age);

        if (ObjectUtils.isEmpty(vehicleNumbers)) {
            throw new ParkingLotException(ExceptionMessages.PROCESSING_ERROR.getMessage());
        }

        if(vehicleNumbers.size() == 0){

            return "No vehicles were parked with the given driver age" + age;

        }

        Collections.sort(vehicleNumbers);

        return String.join(",", vehicleNumbers);
    }

    /*
    * This method is responsible for fetching the slot number given vehicle registration number
    *
    * */

    @Override
    public String getSlotNumberGivenVehicleRegistrationNumber(String vehicleRegistrationNumber) throws ParkingLotException {

        if(StringUtils.isEmpty(vehicleRegistrationNumber)){

            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "vehicleRegistrationNumber"));

        }
        
        BookedSlotDetails bookedSlotDetails = parkingLotDataManager.getSlotNumberGivenVehicleRegistrationNumber(vehicleRegistrationNumber);

        if(ObjectUtils.isEmpty(bookedSlotDetails)){

            return "No vehicle is parked with the given registration number "+vehicleRegistrationNumber;

        }

        return bookedSlotDetails.getVehicleDetails().getType() + " with vehicle registration number " + vehicleRegistrationNumber + " has been parked at " + bookedSlotDetails.getSlot().getId();
    }

    /*
    * This method is responsible for returning the slot numbers of the vehicles which are parked by drivers of a particular age
    * */

    @Override
    public String getSlotNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException {

        if (ObjectUtils.isEmpty(age)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "age"));
        }

        if (age < 0L || age > 1000L) {
            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "age"));
        }

        List<Long> slotNumbers = parkingLotDataManager.getSlotNumberWhichAreDrivenByDriversOfParticularAge(age);

        if(ObjectUtils.isEmpty(slotNumbers)){

            throw new ParkingLotException(ExceptionMessages.PROCESSING_ERROR.getMessage());

        }

        if(slotNumbers.size() == 0){

            return "No vehicles were parked with the given driver age " + age;

        }

        Collections.sort(slotNumbers);

        return slotNumbers.stream().
                map(Object::toString).
                collect(Collectors.joining(","));
    }
}
