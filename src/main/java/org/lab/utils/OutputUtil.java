package org.lab.utils;

import java.time.LocalTime;

public class OutputUtil {
    public static void print(String message) {
        System.out.println("<" + LocalTime.now() + "> " + Thread.currentThread().getName() + ": " + message);
    }
}


