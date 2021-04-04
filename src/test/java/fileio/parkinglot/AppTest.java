package fileio.parkinglot;

import fileio.parkinglot.exeptions.ExceptionMessages;
import fileio.parkinglot.exeptions.ParkingLotException;
import fileio.parkinglot.model.CarDetails;
import fileio.parkinglot.model.DriverDetails;
import fileio.parkinglot.model.VehicleDetails;
import fileio.parkinglot.service.ParkingService;
import fileio.parkinglot.service.impl.ParkingServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final Long INVALID_AGE_VALUE = -6L;

    private static final Integer INVALID_CAPACITY_VALUE = -6;


    @Rule
    public ExpectedException thrownExpectedException = ExpectedException.none();

    /*********  Creating Parking Lot Test cases started  **********/

    @Test
    public void createParkingLot() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));
        
    }

    @Test
    public void alreadyExistParkingLot() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));
        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is(ExceptionMessages.PARKING_ALREADY_EXIST.getMessage()));
        instance.createParkingLot(6);
    }

    @Test
    public void testCreatingParkingLotWithInvalidCapacity() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("capacity value is incorrect"));
        instance.createParkingLot(INVALID_CAPACITY_VALUE);

    }

    /********* Creating Parking Lot Test cases ended  **********/

    /********* Booking Parking Slot Test cases started  **********/

    @Test
    public void testAssignParkingSlotForAVehicle() throws Exception {

        ParkingServiceImpl instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails, driverDetails);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        
    }

    @Test
    public void testAssignParkingSlotForAVehicleWhenVehicleDetailsIsNull() throws Exception {

        ParkingServiceImpl instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("vehicleDetails cannot be null or empty"));

        DriverDetails driverDetails = new DriverDetails(21L);
        instance.parkVehicle(null, driverDetails);

        
    }

    @Test
    public void testAssignParkingSlotForAVehicleWhenDriverDetailsIsNull() throws Exception {

        ParkingServiceImpl instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("driverDetails cannot be null or empty"));

        VehicleDetails vehicleDetails = new CarDetails("KA-01-HH-1234");
        instance.parkVehicle(vehicleDetails, null);

    }

    @Test
    public void assignParkingSlotForMultipleVehicles() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("KA-02-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(22L);
        parkingVehicleOutput = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberKA-02-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

    }

    @Test
    public void assignParkingSlotsCompletelyAndTestingTheParkingFullCase() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(1);
        assertTrue("Createdparkingof1slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is(ExceptionMessages.PARKING_DOES_NOT_EXIST_ERROR.getMessage()));

        VehicleDetails vehicleDetails2 = new CarDetails("KA-02-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(22L);
        parkingVehicleOutput = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberKA-02-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

    }

    /********* Booking Parking Slot Test cases ended  **********/

    /********* Leaving Parking Slot Test cases started  **********/

    @Test
    public void testingLeavingTheVehicle() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(1);
        assertTrue("Createdparkingof1slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));
        
    }

    @Test
    public void testingLeavingTheVehicleWhenSlotIdIsNull() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(1);
        assertTrue("Createdparkingof1slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("slotId cannot be null or empty"));

        instance.leaveVehicle(null);
        
    }

    @Test
    public void testingParkingAndLeavingMultipleVehicles() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        String leaveVehicleOutput2 = instance.leaveVehicle(3L);
        assertTrue("Slotnumber3vacatedtheCarwithregistrationnumberC-01-HH-1234leftthespace,thedriverofthecarwasoftheage22".equalsIgnoreCase(leaveVehicleOutput2.trim().replace(" ", "")));

    }

    /********* Leaving Parking Slot Test cases ended  **********/

    /*********VehicleNumberWhichAreDrivenByDriversOfParticularAge Test cases started  **********/

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAge() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        List<String> expectedVehicleNumbers = new ArrayList<>();
        expectedVehicleNumbers.add("B-01-HH-1234");
        expectedVehicleNumbers.add("C-01-HH-1234");

        Collections.sort(expectedVehicleNumbers);

        String expectedOutPut = String.join(",", expectedVehicleNumbers);

        String vehiclesNumbers = instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(22L);

        assertEquals(expectedOutPut, vehiclesNumbers);

    }

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAgeButVehiclesAreNotPresent() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        String expectedOutPut = "No vehicles were parked with the given driver age24";

        String vehiclesNumbers = instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(24L);

        assertEquals(expectedOutPut, vehiclesNumbers);

    }

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeIsNull() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age cannot be null or empty"));

        instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(null);

    }

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeValueIsInvalid() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age value is incorrect"));

        instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(INVALID_AGE_VALUE);

    }

    /*********VehicleNumberWhichAreDrivenByDriversOfParticularAge Test cases ended  **********/

    /*********SlotNumberGivenVehicleRegistrationNumber Test cases started  **********/

    @Test
    public void testingSlotNumberGivenVehicleRegistrationNumber() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        String expectedOutPut = "carwithvehicleregistrationnumberC-01-HH-1234hasbeenparkedat3";

        String obtainedOutput = instance.getSlotNumberGivenVehicleRegistrationNumber("C-01-HH-1234");

        assertTrue(expectedOutPut.equalsIgnoreCase(obtainedOutput.replace(" ", "")));

    }

    @Test
    public void testingSlotNumberGivenVehicleRegistrationNumberWhenNumberIsNull() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("vehicleRegistrationNumber cannot be null or empty"));

        instance.getSlotNumberGivenVehicleRegistrationNumber(null);
    }

    @Test
    public void testingSlotNumberGivenVehicleRegistrationNumberWhenVehicleDoesNotExists() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        String expectedOutPut = "NovehicleisparkedwiththegivenregistrationnumberC-01-HH-1235";

        String obtainedOutput = instance.getSlotNumberGivenVehicleRegistrationNumber("C-01-HH-1235");

        assertTrue(expectedOutPut.equalsIgnoreCase(obtainedOutput.replace(" ", "")));

    }


    /*********SlotNumberGivenVehicleRegistrationNumber Test cases ended  **********/

    /*********SlotNumberWhichAreDrivenByDriversOfParticularAge Test cases started  **********/

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAge() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        List<String> expectedVehicleNumbers = new ArrayList<>();
        expectedVehicleNumbers.add("2");
        expectedVehicleNumbers.add("3");

        Collections.sort(expectedVehicleNumbers);

        String expectedOutPut = String.join(",", expectedVehicleNumbers);

        String vehiclesNumbers = instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(22L);

        assertEquals(expectedOutPut, vehiclesNumbers);
    }

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAgeButVehiclesAreNotPresent() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        String expectedOutPut = "No vehicles were parked with the given driver age 25";

        String obtainedOutput = instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(25L);

        assertEquals(expectedOutPut, obtainedOutput);

    }

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeIsNull() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age cannot be null or empty"));

        instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(null);

    }

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeValueIsInvalid() throws Exception {

        ParkingService instance = new ParkingServiceImpl();

        initialiseTheParkingLotForTesting(instance);

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age value is incorrect"));

        instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(INVALID_AGE_VALUE);

    }

    /*********SlotNumberWhichAreDrivenByDriversOfParticularAge Test cases ended  **********/

    private void initialiseTheParkingLotForTesting(ParkingService instance) throws ParkingLotException {

        instance.createParkingLot(6);

        VehicleDetails vehicleDetails = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails = new DriverDetails(21L);
        instance.parkVehicle(vehicleDetails, driverDetails);

        instance.leaveVehicle(1L);

        vehicleDetails = new CarDetails("A-01-HH-1234");
        driverDetails = new DriverDetails(21L);
        instance.parkVehicle(vehicleDetails, driverDetails);

        vehicleDetails = new CarDetails("B-01-HH-1234");
        driverDetails = new DriverDetails(22L);
        instance.parkVehicle(vehicleDetails, driverDetails);

        vehicleDetails = new CarDetails("C-01-HH-1234");
        driverDetails = new DriverDetails(22L);
        instance.parkVehicle(vehicleDetails, driverDetails);
    }

}
