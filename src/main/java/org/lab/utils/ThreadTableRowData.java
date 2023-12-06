package org.lab.utils;

public class ThreadTableRowData {
    private final String name;
    private Thread.State state;
    private int priority;
    private String lastMessage;
    private String lastStatusChangeTime;

    public ThreadTableRowData(String name, Thread.State state, int priority, String lastMessage) {
        this.name = name;
        this.state = state;
        this.priority = priority;
        this.lastMessage = lastMessage;
        this.lastStatusChangeTime = getCurrentTime();
    }

    private String getCurrentTime() {
        return java.time.LocalTime.now().toString();
    }

    public String getLastStatusChangeTime() {
        return lastStatusChangeTime;
    }

    public String getName() {
        return name;
    }

    public Thread.State getState() {
        return state;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setState(Thread.State state) {
        this.state = state;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        this.lastStatusChangeTime = getCurrentTime();
    }

    public int getPriority() {
        return priority;
    }

}