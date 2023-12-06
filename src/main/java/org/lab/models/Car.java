package org.lab.models;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Random;

public class Car extends Thread implements Vehicle {

    private final String carName;
    private final Random random = new Random();
    private ParkingLot parkingLot = null;
    private final int timeToStayParked = random.nextInt(5, 12);
    public Object lock;
    private boolean isPaused = false;
    private boolean isParked = false;

    private final StringProperty currentState = new SimpleStringProperty("Car is not running");

    public String getCurrentState() {
        return currentState.get();
    }

    public void setCurrentState(String currentState) {
        this.currentState.set(currentState);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        currentState.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        currentState.removeListener(listener);
    }

    public Car(String carName) {
        this.carName = carName;
    }

    public void selectParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getCarName() {
        return carName;
    }
    public void setIsParked(boolean isParked) {
        this.isParked = isParked;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(carName);
        Thread.currentThread().setPriority(random.nextInt(1, 11));

        if (parkingLot == null) {
            setCurrentState("No parking lot selected");
            return;
        }

        while (true) {
            if (isPaused) {
                synchronized (lock) {
                    try {
                        while (isPaused) {
                            setCurrentState("Car is paused");
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        setCurrentState("Car was interrupted while parking");
                    }
                }
                continue;
            }
            var isSuccededParked = drive();
            if (!isSuccededParked) {
                continue;
            }
            stayParked();
            drive();
        }
    }


    public void pause() {
        isPaused = true;
    }

    public void unpause() {
        isPaused = false;
        if (isParked) {
            parkingLot.leaveParkingSpot(this);
        }
        isParked = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private boolean drive() {
        setCurrentState("Car is driving");
        if (!isParked) {
            setCurrentState("Car is trying to park");
            parkingLot.parkCar(this);
        } else {
            setCurrentState("Car leaved parking spot");
            parkingLot.leaveParkingSpot(this);
        }
        return this.isParked;
    }

    public void stayParked() {
        setCurrentState("Car is parked for " + timeToStayParked + " seconds");
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        synchronized (lock) {
            while (elapsedTime < timeToStayParked * 1000L && isParked) {
                try {
                    lock.wait(timeToStayParked * 1000L - elapsedTime);
                } catch (InterruptedException e) {
                    setCurrentState("Car was interrupted while being parked");
                    return;
                }
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        }
    }
}
