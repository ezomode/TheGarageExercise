package com.wreck.garage.one;

import com.wreck.model.Vehicle;

import java.util.*;
import java.util.stream.IntStream;

class Level {
    private final int capacity;
    private final int levelNumber;
    private final List<ParkingSpace> freeParkingSpaces;
    private final Map<UUID, ParkingSpace> occupiedSpacesMap;

    Level(int capacity, int levelNumber) {
        this.capacity = capacity;
        this.levelNumber = levelNumber;
        freeParkingSpaces = new LinkedList<>();
        occupiedSpacesMap = new HashMap<>();

        // Todo: optimize.
        IntStream.range(0, capacity).forEach(i -> {
            freeParkingSpaces.add(new ParkingSpace(levelNumber, i));
        });
    }

    public Map<UUID, ParkingSpace> getOccupiedSpacesMap() {
        return occupiedSpacesMap;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public boolean isFull() {
        return freeParkingSpaces.size() < 1 || occupiedSpacesMap.size() >= capacity;
    }

    public boolean remove(Vehicle vehicle) {

        if (vehicle == null) {
            return false;
        }

        ParkingSpace parkingSpace = occupiedSpacesMap.remove(vehicle.getUuid());
        freeParkingSpaces.add(parkingSpace);

        return true;
    }

    public boolean insert(Vehicle vehicle) {

        if (isFull() || vehicle == null) {
            return false;
        }

        ParkingSpace parkingSpace = freeParkingSpaces.remove(0);
        occupiedSpacesMap.put(vehicle.getUuid(), parkingSpace);

        return true;
    }
}
