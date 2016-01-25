package com.wreck;

import com.wreck.garage.one.Garage;
import com.wreck.garage.one.GarageImpl;
import com.wreck.model.Car;
import com.wreck.model.Motorbike;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Garage g = GarageImpl.create(3, 10);

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        System.out.println(g.getAvailableSpaces());

        System.out.println(g.insert(car1));
        System.out.println(g.insert(car1));
        System.out.println(g.insert(car2));
        System.out.println(g.insert(car3));

        List<Motorbike> bikes = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            bikes.add(new Motorbike());
        }

        for (Motorbike mb : bikes) {
            System.out.println(g.insert(mb));
            System.out.println(g.getVehiclePosition(mb).get());
        }

        System.out.println(g.getVehiclePosition(null));

        System.out.println(g.getAvailableSpaces());

        for (Motorbike mb : bikes) {
            System.out.println(g.remove(mb));
        }

        System.out.println(g.getAvailableSpaces());

        System.out.println(g.remove(car1));
        System.out.println(g.remove(car1));

        System.out.println(g.getAvailableSpaces());
    }
}
