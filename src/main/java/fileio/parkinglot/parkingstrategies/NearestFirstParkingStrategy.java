package fileio.parkinglot.parkingstrategies;

import fileio.parkinglot.model.Slot;

import java.util.Comparator;
import java.util.TreeSet;

//This class implement the strategy pattern
// This will give the nearest free slot available in the available slots
public class NearestFirstParkingStrategy implements ParkingStrategy {

    private TreeSet<Slot> freeSlots;

    public NearestFirstParkingStrategy() {
        freeSlots = new TreeSet<Slot>(Comparator.comparing(Slot::getId));
    }

    @Override
    public void addFreeSlot(Slot slot) {
        freeSlots.add(slot);
    }

    @Override
    public Slot getFreeSlot() {
        return freeSlots.first();
    }

    @Override
    public boolean isGivenSlotFree(Slot slot) {
        return freeSlots.contains(slot);
    }

    @Override
    public boolean isGivenSlotOccupied(Slot slot) {
        return !isGivenSlotFree(slot);
    }

    @Override
    public void removeFreeSlot(Slot availableSlot) {
        freeSlots.remove(availableSlot);
    }

    @Override
    public boolean areFreeSlotsAvailable(){

        return freeSlots.size()>0;

    }
}
