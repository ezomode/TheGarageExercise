package com.wreck.garage;

import com.wreck.model.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/*
* This version has O(1) insert/remove, but uses O(n*m) memory.
* */
public class GarageImpl implements Garage {

    private final int levelsNumber;
    private final int garageCapacity;

    private final Map<Integer, Level> sparseLevels;
    private final Map<Integer, Level> packedLevels;
    private final Map<UUID, ParkingSpace> uuidMap;

    private GarageImpl(int levelsNumber, int levelCapacity) {

        if (levelsNumber < 1 || levelCapacity < 1) {
            throw new IllegalArgumentException("Either number of levels or level capacity were specified with value less than 1.");
        }

        this.levelsNumber = levelsNumber;
        this.garageCapacity = levelsNumber * levelCapacity;
        this.uuidMap = new HashMap<>();

        sparseLevels = new HashMap<>();
        packedLevels = new HashMap<>();

        IntStream.range(0, levelsNumber).forEach(
                i -> sparseLevels.put(i, new Level(levelCapacity, i))
        );
    }

    public static Garage create(int levelsNumber, int levelCapacity) {
        return new GarageImpl(levelsNumber, levelCapacity);
    }

    @Override
    public int getAvailableSpaces() {
        return garageCapacity - uuidMap.size();
    }

    @Override
    public boolean insert(Vehicle vehicle) {
        if (isGarageFull() || vehicle == null || isParked(vehicle)) {
            return false;
        }

        Integer key = sparseLevels.keySet().iterator().next();
        Level level = sparseLevels.get(key);
        level.insert(vehicle);

        UUID vehicleUuid = vehicle.getUuid();
        ParkingSpace parkingSpace = level.getOccupiedSpacesMap().get(vehicleUuid);
        uuidMap.put(vehicleUuid, parkingSpace);

        if (level.isFull()) {
            int levelNumber = level.getLevelNumber();
            packedLevels.put(levelNumber, level);
            sparseLevels.remove(levelNumber);
        }

        return true;
    }

    @Override
    public boolean remove(Vehicle vehicle) {

        if (vehicle == null || !isParked(vehicle)) {
            return false;
        }

        ParkingSpace parkingSpace = uuidMap.remove(vehicle.getUuid());

        int levelNumber = parkingSpace.getLevelNumber();

        sparseLevels.computeIfPresent(levelNumber, (key, level) -> {
            level.remove(vehicle);

            return level;
        });

        if (packedLevels.containsKey(levelNumber)) {
            Level level = packedLevels.remove(levelNumber);
            level.remove(vehicle);
            sparseLevels.put(levelNumber, level);
        }

        return true;
    }

    private boolean isParked(Vehicle vehicle) {
        return uuidMap.containsKey(vehicle.getUuid());
    }

    // Wrap with Optional to avoid returning null when vehicle is not there.
    @Override
    public Optional<ParkingSpace> getVehiclePosition(Vehicle vehicle) {
        if (vehicle == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(uuidMap.get(vehicle.getUuid()));
    }

    /*
    * Extensive consistency checks here.
    * */
    private boolean isGarageFull() {
        return sparseLevels.size() < 1
                || packedLevels.size() >= levelsNumber
                || uuidMap.size() >= garageCapacity;
    }
}
