package org.lab.managers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import org.lab.utils.ThreadTableRowData;
import org.lab.utils.OutputUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarListener implements InvalidationListener {

    private final String carName;
    private final TableView<ThreadTableRowData> table;
    private final Lock lock = new ReentrantLock();

    public CarListener(String carName, TableView<ThreadTableRowData> table) {
        this.table = table;
        this.carName = carName;
    }

    @Override
    public void invalidated(Observable observable) {
        OutputUtil.print(((StringProperty)observable).getValue());
        if (lock.tryLock()) {
            try {
                table.getItems().stream().filter(threadTableRowData -> threadTableRowData.getName().equals(carName)).forEach(threadTableRowData -> {
                    threadTableRowData.setLastMessage(((StringProperty) observable).getValue());
                    threadTableRowData.setPriority(Thread.currentThread().getPriority());
                    table.refresh();
                });
            } finally {
                lock.unlock();
            }
        }
    }
}
