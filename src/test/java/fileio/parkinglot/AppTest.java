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


    @Rule
    public ExpectedException thrownExpectedException = ExpectedException.none();


    @Test
    public void createParkingLot() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));
        instance.destroy();
    }

    @Test
    public void alreadyExistParkingLot() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));
        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is(ExceptionMessages.PARKING_ALREADY_EXIST.getMessage()));
        instance.createParkingLot(6);
        instance.destroy();
    }

    @Test
    public void testCreatingParkingLotWithInvalidCapacity() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("capacity value is incorrect"));
        instance.createParkingLot(-6);
        instance.destroy();

    }

    @Test
    public void testAssignParkingSlotForAVehicle() throws Exception {

        ParkingServiceImpl instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails, driverDetails);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        instance.destroy();
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

        instance.destroy();
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

        instance.destroy();
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

        instance.destroy();
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
        thrownExpectedException.expectMessage(is(ExceptionMessages.PARKING_NOT_EXIST_ERROR.getMessage()));

        VehicleDetails vehicleDetails2 = new CarDetails("KA-02-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(22L);
        parkingVehicleOutput = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberKA-02-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        instance.destroy();
    }

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

        instance.destroy();
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

        instance.destroy();
    }

    @Test
    public void testingParkingAndLeavingMultipleVehicles() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        String leaveVehicleOutput2 = instance.leaveVehicle(3L);
        assertTrue("Slotnumber3vacatedtheCarwithregistrationnumberC-01-HH-1234leftthespace,thedriverofthecarwasoftheage22".equalsIgnoreCase(leaveVehicleOutput2.trim().replace(" ", "")));

        instance.destroy();
    }

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAge() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        List<String> expectedVehicleNumbers = new ArrayList<>();
        expectedVehicleNumbers.add("B-01-HH-1234");
        expectedVehicleNumbers.add("C-01-HH-1234");

        Collections.sort(expectedVehicleNumbers);

        String expectedOutPut = String.join(",", expectedVehicleNumbers);

        String vehiclesNumbers = instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(22L);

        assertEquals(expectedOutPut, vehiclesNumbers);

        instance.destroy();

    }

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeIsNull() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));


        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age cannot be null or empty"));

        instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(null);

        instance.destroy();

    }

    @Test
    public void testingVehicleNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeValueIsInvalid() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));


        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age value is incorrect"));

        instance.getVehicleNumberWhichAreDrivenByDriversOfParticularAge(-5L);

        instance.destroy();

    }


    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAge() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        List<String> expectedVehicleNumbers = new ArrayList<>();
        expectedVehicleNumbers.add("2");
        expectedVehicleNumbers.add("3");

        Collections.sort(expectedVehicleNumbers);

        String expectedOutPut = String.join(",", expectedVehicleNumbers);

        String vehiclesNumbers = instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(22L);

        assertEquals(expectedOutPut, vehiclesNumbers);

        instance.destroy();

    }

    @Test
    public void testingSlotNumberGivenVehicleRegistrationNumber() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        String expectedOutPut = "carwithvehicleregistrationnumberC-01-HH-1234hasbeenparkedat3";

        String obtainedOutput = instance.getSlotNumberGivenVehicleRegistrationNumber("C-01-HH-1234");

        assertTrue(expectedOutPut.equalsIgnoreCase(obtainedOutput.replace(" ", "")));

        instance.destroy();

    }

    @Test
    public void testingSlotNumberGivenVehicleRegistrationNumberWhenNumberIsNull() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("vehicleRegistrationNumber cannot be null or empty"));

        instance.getSlotNumberGivenVehicleRegistrationNumber(null);

        instance.destroy();

    }

    @Test
    public void testingSlotNumberGivenVehicleRegistrationNumberWhenVehicleDoesNotExists() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        String expectedOutPut = "NovehicleisparkedwiththegivenregistrationnumberC-01-HH-1235";

        String obtainedOutput = instance.getSlotNumberGivenVehicleRegistrationNumber("C-01-HH-1235");

        assertTrue(expectedOutPut.equalsIgnoreCase(obtainedOutput.replace(" ", "")));

        instance.destroy();

    }

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAgeButVehiclesAreNotPresent() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        String expectedOutPut = "No vehicles were parked with the given driver age 25";

        String obtainedOutput = instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(25L);

        assertEquals(expectedOutPut, obtainedOutput);

        instance.destroy();

    }

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeIsNull() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age cannot be null or empty"));

        instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(null);


        instance.destroy();

    }

    @Test
    public void testingSlotNumberWhichAreDrivenByDriversOfParticularAgeWhenAgeValueIsInvalid() throws Exception {

        ParkingService instance = new ParkingServiceImpl();
        String output = instance.createParkingLot(6);
        assertTrue("Createdparkingof6slots".equalsIgnoreCase(output.trim().replace(" ", "")));

        VehicleDetails vehicleDetails1 = new CarDetails("KA-01-HH-1234");
        DriverDetails driverDetails1 = new DriverDetails(21L);
        String parkingVehicleOutput = instance.parkVehicle(vehicleDetails1, driverDetails1);
        assertTrue("CarwithvehicleregistrationNumberKA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput.trim().replace(" ", "")));

        String leaveVehicleOutput = instance.leaveVehicle(1L);
        assertTrue("Slotnumber1vacatedtheCarwithregistrationnumberKA-01-HH-1234leftthespace,thedriverofthecarwasoftheage21".equalsIgnoreCase(leaveVehicleOutput.trim().replace(" ", "")));

        VehicleDetails vehicleDetails2 = new CarDetails("A-01-HH-1234");
        DriverDetails driverDetails2 = new DriverDetails(21L);
        String parkingVehicleOutput2 = instance.parkVehicle(vehicleDetails2, driverDetails2);
        assertTrue("CarwithvehicleregistrationNumberA-01-HH-1234hasbeenparkedatslotnumber1".equalsIgnoreCase(parkingVehicleOutput2.trim().replace(" ", "")));

        VehicleDetails vehicleDetails3 = new CarDetails("B-01-HH-1234");
        DriverDetails driverDetails3 = new DriverDetails(22L);
        String parkingVehicleOutput3 = instance.parkVehicle(vehicleDetails3, driverDetails3);
        assertTrue("CarwithvehicleregistrationNumberB-01-HH-1234hasbeenparkedatslotnumber2".equalsIgnoreCase(parkingVehicleOutput3.trim().replace(" ", "")));

        VehicleDetails vehicleDetails4 = new CarDetails("C-01-HH-1234");
        DriverDetails driverDetails4 = new DriverDetails(22L);
        String parkingVehicleOutput4 = instance.parkVehicle(vehicleDetails4, driverDetails4);
        assertTrue("CarwithvehicleregistrationNumberC-01-HH-1234hasbeenparkedatslotnumber3".equalsIgnoreCase(parkingVehicleOutput4.trim().replace(" ", "")));

        thrownExpectedException.expect(ParkingLotException.class);
        thrownExpectedException.expectMessage(is("age value is incorrect"));

        instance.getSlotNumberWhichAreDrivenByDriversOfParticularAge(-6L);


        instance.destroy();

    }

}
