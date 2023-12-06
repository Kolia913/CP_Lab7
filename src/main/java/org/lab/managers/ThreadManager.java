package org.lab.managers;

import org.lab.models.ParkingLot;
import org.lab.utils.ThreadTableRowData;
import org.lab.models.Car;

import java.util.ArrayList;
import java.util.List;

public class ThreadManager {
    private final ParkingLot parkingLot;
    private final List<Car> cars;
    private final Object lock = new Object();

    public ThreadManager(int numberOfCars) {
        var parkingSpotsNum = numberOfCars - 2 > 0 ? numberOfCars - 2 : 1;
        this.parkingLot = new ParkingLot(parkingSpotsNum);
        this.cars = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            Car car = new Car("Thread " + i);
            car.selectParkingLot(parkingLot);
            cars.add(car);
        }
    }

    public void start() {
        System.out.println("Started threading");
        try {
            for (Car car : cars) {
                car.addListener(new CarListener(car.getCarName(), GuiManager.getTable()));
                car.lock = lock;
                car.start();
                GuiManager.addTableRow(new ThreadTableRowData(car.getCarName(), car.getState(), car.getPriority(), car.getCurrentState()));
            }
            Thread checkThreadStates = new Thread(this::checkThreadStates);
            checkThreadStates.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Car findCarByName(String carName) {
        for (Car car : cars) {
            if (car.getCarName().equals(carName)) {
                return car;
            }
        }
        return null;
    }

    public void pauseThread(String threadName) {
        Car thread = findCarByName(threadName);
        if (thread != null) {
            thread.pause();
        }
    }

    public void resumeThread(String threadName) {
        Car thread = findCarByName(threadName);
        if (thread != null) {
            thread.unpause();
        }
    }

    public boolean isAnyRunning() {
        for (Thread thread : cars) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public void checkThreadStates() {
        while (isAnyRunning()) {
            for (Thread carThread : cars) {
                GuiManager.updateTableRow(carThread.getName(), carThread.getState());
            }
        }
        for (Thread carThread : cars) {
            GuiManager.updateTableRow(carThread.getName(), carThread.getState());
        }
    }
}
