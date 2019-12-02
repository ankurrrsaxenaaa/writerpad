package com.xebia.fs101.writerpad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.nio.file.Files.readAllLines;

@Service
public class SpamChecker {

    @Autowired
    private ResourceLoader resourceLoader;

    private List<String> lines;

    @PostConstruct
    public void init() throws IOException {
        File file = resourceLoader.getResource("classpath:Spam.txt").getFile();
        lines = readAllLines(file.toPath());
    }

    public boolean isSpam(String content) throws IOException {
        String[] words = content.toLowerCase().split("\\s");
        for (String word : words) {
            if (lines.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
