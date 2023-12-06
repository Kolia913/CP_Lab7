package org.lab.models;

public class ParkingPlace {
    private boolean isAvailable;
    private Vehicle car;

    public ParkingPlace() {
        this.isAvailable = true;
    }

    public void putCarOnSpot(Vehicle car) {
        this.car = car;
        this.isAvailable = false;
    }

    public Vehicle getCarOnSpot() {
        return car;
    }

    public void removeCarFromSpot() {
        this.car = null;
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Vehicle getCar() {
        return car;
    }
}
