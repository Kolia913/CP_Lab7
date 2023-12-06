package org.lab.models;

import javafx.beans.Observable;

public interface Vehicle extends Runnable, Observable {
    void run();
}
