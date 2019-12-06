package com.xebia.fs101.writerpad.api.representations;

public class TimeToRead {
    private int mins;
    private int seconds;

    public TimeToRead() {
    }

    public TimeToRead(int mins, int seconds) {
        this.mins = mins;
        this.seconds = seconds;
    }

    public int getMins() {
        return mins;
    }

    public void setMins(int mins) {
        this.mins = mins;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setReadingTime(int seconds, int minutes) {
        this.mins = minutes;
        this.seconds = seconds;
    }
}
