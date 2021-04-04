package fileio.parkinglot.utils;

import java.util.HashMap;
import java.util.Map;

public class ParkingCommandConstants {

    public static final String	CREATE_PARKING_LOT	= "Create_parking_lot";

    public static final String PARK = "Park";

    public static final String LEAVE = "Leave";

    public static final String SLOT_NUMBERS_FOR_DRIVER_OF_AGE = "Slot_numbers_for_driver_of_age";

    public static final String SLOT_NUMBER_FOR_CAR_WITH_NUMBER = "Slot_number_for_car_with_number";

    public static final String VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE = "Vehicle_registration_number_for_driver_of_age";

    public static final Map<String, Integer> COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP = new HashMap<>();

    static {

        COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP.put(CREATE_PARKING_LOT,2);
        COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP.put(PARK,4);
        COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP.put(LEAVE,2);
        COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP.put(SLOT_NUMBERS_FOR_DRIVER_OF_AGE,2);
        COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP.put(SLOT_NUMBER_FOR_CAR_WITH_NUMBER,2);
        COMMAND_VS_NUMBER_OF_ARGUMENTS_MAP.put(VEHICLE_REGISTRATION_NUMBERS_FOR_DRIVER_OF_AGE,2);


    }

}
