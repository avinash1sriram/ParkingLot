package fileio.parkinglot.dao;

import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.model.BookedSlotDetails;
import fileio.parkinglot.model.DriverDetails;
import fileio.parkinglot.model.VehicleDetails;
import fileio.parkinglot.parkingstrategies.ParkingStrategy;

import java.util.List;

public interface ParkingLotDataManager<T extends VehicleDetails> {

    BookedSlotDetails parkVehicle(T vehicleDetails, DriverDetails driverDetails) throws ParkingLotException;

    BookedSlotDetails leaveVehicle(Long slotId) throws ParkingLotException;

    List<String> getVehicleNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException;

    BookedSlotDetails getSlotNumberGivenVehicleRegistrationNumber(String vehicleRegistrationNumber) throws ParkingLotException;

    List<Long> getSlotNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException;
}
