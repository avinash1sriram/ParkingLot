package fileio.parkinglot.parkingstrategies;

import fileio.parkinglot.model.Slot;

/*
* To decide the parking the slot, there can be many ways down the line as the application/ use cases expanding
* In order to decouple the different logics in decision making
* We are using the strategy pattern
* */

public interface ParkingStrategy {

    void removeFreeSlot(Slot slot);
    void addFreeSlot(Slot slot);
    Slot getFreeSlot();
    boolean isGivenSlotFree(Slot slot);
    boolean isGivenSlotOccupied(Slot slot);
    boolean areFreeSlotsAvailable();

}
