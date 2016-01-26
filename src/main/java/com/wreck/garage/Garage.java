package com.wreck.garage;

import com.wreck.model.Vehicle;

import java.util.Optional;

public interface Garage {

    int getAvailableSpaces();

    boolean insert(Vehicle vehicle);

    boolean remove(Vehicle vehicle);

    Optional<ParkingSpace> getVehiclePosition(Vehicle vehicle);
}
