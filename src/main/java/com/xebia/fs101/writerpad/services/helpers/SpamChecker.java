package com.xebia.fs101.writerpad.services.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.nio.file.Files.readAllLines;

@Service
public class SpamChecker {

    @Value("${spam.file}")
    File file;
    private List<String> lines;

    public boolean isSpam(String content) throws IOException {
        lines = readAllLines(file.toPath());
        String[] words = content.toLowerCase().split("\\s");
        for (String word : words) {
            if (lines.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
