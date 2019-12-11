package com.xebia.fs101.writerpad.services.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    @Value("${average.reading.speed.wpm}")
    int wordsPerMinute;

    public int readingTimeInSeconds(String body) {
        return (body.split("\\s").length / wordsPerMinute);
    }

}
