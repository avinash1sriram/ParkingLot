package fileio.parkinglot.service;

import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.model.DriverDetails;
import fileio.parkinglot.model.VehicleDetails;

public interface ParkingService {

    String createParkingLot(int capacity) throws ParkingLotException;

    String parkVehicle(VehicleDetails vehicleDetails, DriverDetails driverDetails) throws ParkingLotException;

    String leaveVehicle(Long slotId) throws ParkingLotException;

    String getVehicleNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException;

    String getSlotNumberGivenVehicleRegistrationNumber(String vehicleRegistrationNumber) throws ParkingLotException;

    String getSlotNumberWhichAreDrivenByDriversOfParticularAge(Long age) throws ParkingLotException;
}
