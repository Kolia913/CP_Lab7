package org.lab.models;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final List<ParkingPlace> parkingPlaces = new ArrayList<>();

    public ParkingLot(int freeParkingSpots) {
        for (int i = 0; i < freeParkingSpots; i++) {
            parkingPlaces.add(new ParkingPlace());
        }
    }

    public synchronized void parkCar(Car car) {
        for (ParkingPlace parkingPlace : parkingPlaces) {
                if (parkingPlace.isAvailable()) {
                    parkingPlace.putCarOnSpot(car);
                    car.setIsParked(true);
                break;
            }
        }
    }

    public synchronized void leaveParkingSpot(Car car) {
            for (ParkingPlace parkingPlace : parkingPlaces) {
                if (parkingPlace.getCarOnSpot() == car) {
                    parkingPlace.removeCarFromSpot();
                    car.setIsParked(false);
                    break;
                }
            }
    }
}
