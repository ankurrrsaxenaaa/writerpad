package com.xebia.fs101.writerpad.utilities;

public abstract class TimeUtil {

    public static int getSeconds(String body, int wordsPerMinute) {
        return (body.split(" ").length / wordsPerMinute) % 60;
    }

    public static int getMinutes(String body, int wordsPerMinute) {
        return (body.split(" ").length / wordsPerMinute) / 60;
    }
}
