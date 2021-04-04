package fileio.parkinglot.dao.impl;

import fileio.parkinglot.dao.ParkingLotDataManager;
import fileio.parkinglot.exeptions.ExceptionMessages;
import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.model.BookedSlotDetails;
import fileio.parkinglot.model.DriverDetails;
import fileio.parkinglot.model.Slot;
import fileio.parkinglot.model.VehicleDetails;
import fileio.parkinglot.parkingstrategies.NearestFirstParkingStrategy;
import fileio.parkinglot.parkingstrategies.ParkingStrategy;
import fileio.parkinglot.utils.ObjectUtils;
import fileio.parkinglot.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotDataManagerImpl<T extends VehicleDetails> implements ParkingLotDataManager<T> {


    private ParkingStrategy parkingStrategy;

    private Map<Slot, BookedSlotDetails> bookedSlotDetailsMap;

    private Map<Long,Slot> idVsSlotMap;

    private static ParkingLotDataManagerImpl instance = null;


    /*
    * This method is used to initialise the entire memory
    * */
    public static <T extends VehicleDetails> ParkingLotDataManagerImpl<T> intialiseAndCreateParkingLot(int capacity,
                                                                                                       ParkingStrategy parkingStrategy) throws ParkingLotException {
        instance = new ParkingLotDataManagerImpl<T>(capacity, parkingStrategy);
        return instance;
    }

    private ParkingLotDataManagerImpl(int capacity, ParkingStrategy parkingStrategy) {

        this.parkingStrategy = parkingStrategy;

        this.bookedSlotDetailsMap = new HashMap<>();

        this.idVsSlotMap = new HashMap<>();

        if (parkingStrategy == null)
            parkingStrategy = new NearestFirstParkingStrategy();

        for (int i = 1; i <= capacity; i++) {

            Slot slot = new Slot();
            slot.setId((long) i);
            parkingStrategy.addFreeSlot(slot);
            idVsSlotMap.put((long)i,slot);

        }
    }

    /*
    * This method is used to change the entities that are related to park a vehicle
    * It will create a new booked slot details
    * Will remove the slot that is assigned from the free slots
    * */

    @Override
    public BookedSlotDetails parkVehicle(T vehicleDetails, DriverDetails driverDetails) throws ParkingLotException {

        if (ObjectUtils.isEmpty(vehicleDetails)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "vehicleDetails"));
        }
        if (ObjectUtils.isEmpty(driverDetails)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "driverDetails"));
        }

        BookedSlotDetails bookedSlotDetails = null;

        if (parkingStrategy.areFreeSlotsAvailable()) {

            Slot slot = parkingStrategy.getFreeSlot();
            bookedSlotDetails = new BookedSlotDetails(slot, vehicleDetails, driverDetails);
            parkingStrategy.removeFreeSlot(slot);
            bookedSlotDetailsMap.put(slot, bookedSlotDetails);
        }

        return bookedSlotDetails;
    }

    /*
    * This method is available for removing the vehicle from slot
    * Removing the booked slot details from the list
    * Adding the slot to the free slots
    * */

    @Override
    public BookedSlotDetails leaveVehicle(Long slotId) throws ParkingLotException {

        if (ObjectUtils.isEmpty(slotId)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "slotId"));
        }

        BookedSlotDetails bookedSlotDetails = null;

        if (bookedSlotDetailsMap.containsKey(idVsSlotMap.get(slotId))) {

            bookedSlotDetails = bookedSlotDetailsMap.remove(idVsSlotMap.get(slotId));
            parkingStrategy.addFreeSlot(idVsSlotMap.get(slotId));

        }

        return bookedSlotDetails;
    }

    /*
    * This method will iterate over the booked slot details
    * Compare the age present in the booked slot details with age that is passed as input to the function
    * If they matches that will be added to the list
    * */
    @Override
    public List<String> getVehicleNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException {

        if (ObjectUtils.isEmpty(age)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "age"));
        }

        if (age < 0L) {
            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "age"));
        }

        List<String> vehicleRegistrationNumbers = new ArrayList<>();

        for (BookedSlotDetails bookedSlotDetails : bookedSlotDetailsMap.values()) {

            if (age.equals(bookedSlotDetails.getDriverDetails().getAge())) {

                vehicleRegistrationNumbers.add(bookedSlotDetails.getVehicleDetails().getRegistrationNumber());

            }

        }

        return vehicleRegistrationNumbers;
    }

    /*
     * This method will iterate over the booked slot details
     * Compare the vehicle registration number present in the booked slot details with the registration number that is passed as input to the function
     * If they matches that booked slot details will be returned
     * */

    @Override
    public BookedSlotDetails getSlotNumberGivenVehicleRegistrationNumber(String vehicleRegistrationNumber) throws ParkingLotException {

        if(StringUtils.isEmpty(vehicleRegistrationNumber)){

            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "vehicleRegistrationNumber"));

        }

        BookedSlotDetails requiredBookedSlotDetails = null;

        for (BookedSlotDetails bookedSlotDetails : bookedSlotDetailsMap.values()) {

            if (vehicleRegistrationNumber.equals(bookedSlotDetails.getVehicleDetails().getRegistrationNumber())) {

                requiredBookedSlotDetails = bookedSlotDetails;
                break;

            }

        }

        return requiredBookedSlotDetails;
    }

    /*
     * This method will iterate over the booked slot details
     * Compare the age present in the booked slot details with age that is passed as input to the function
     * If they matches slot id corresponding to that booking details will be added to that list
     * */
    @Override
    public List<Long> getSlotNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException {

        if (ObjectUtils.isEmpty(age)) {
            throw new ParkingLotException(ExceptionMessages.NULL_OR_EMPTY.getMessage().replace("{variable}", "age"));
        }

        if (age < 0L) {
            throw new ParkingLotException(ExceptionMessages.INVALID_VALUE.getMessage().replace("{variable}", "age"));
        }

        List<Long> slotNumbers = new ArrayList<>();

        for (BookedSlotDetails bookedSlotDetails : bookedSlotDetailsMap.values()) {

            if (age.equals(bookedSlotDetails.getDriverDetails().getAge())) {

                slotNumbers.add(bookedSlotDetails.getSlot().getId());

            }

        }

        return slotNumbers;
    }

    /*
    * Setting everything to null
    * */
    @Override
    public void destroy()
    {
        this.parkingStrategy = null;
        this.bookedSlotDetailsMap = null;
        this.idVsSlotMap = null;
        instance = null;
    }
}
