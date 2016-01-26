package com.wreck.garage;

import com.wreck.model.Car;
import com.wreck.model.Motorbike;
import com.wreck.model.Vehicle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;


public class GarageTest {

    Garage garage1;
    Garage garage100;
    Garage garage1M;

    Vehicle bike;
    Vehicle car;

    List<Vehicle> bikes30;
    List<Vehicle> cars130;
    List<Vehicle> bikes300K;
    List<Vehicle> cars300K;

    @Before
    public void setup() {
        garage1 = GarageImpl.create(1, 1);
        garage100 = GarageImpl.create(10, 10);
        garage1M = GarageImpl.create(1000, 1000);

        bike = new Motorbike();
        car = new Car();
        bikes30 = new ArrayList<>();
        cars130 = new ArrayList<>();
        bikes300K = new ArrayList<>();
        cars300K = new ArrayList<>();

        IntStream.range(0, 30).forEach(i -> bikes30.add(new Motorbike()));
        IntStream.range(0, 130).forEach(i -> cars130.add(new Car()));
        IntStream.range(0, 300000).forEach(i -> bikes300K.add(new Car()));
        IntStream.range(0, 300000).forEach(i -> cars300K.add(new Car()));
    }

    @Test
    public void testGetAvailableSpaces() throws Exception {
        bikes30.forEach(garage1::insert);
        assertEquals(0, garage1.getAvailableSpaces());

        bikes30.forEach(garage100::insert);
        assertEquals(70, garage100.getAvailableSpaces());

        bikes30.forEach(garage100::insert);
        assertEquals(70, garage100.getAvailableSpaces());
    }
}