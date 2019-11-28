package com.xebia.fs101.writerpad.utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class StringUtil {

    public StringUtil() {
    }

    public static String generateSlug(String str) {
        if (str == null) {
            throw new IllegalArgumentException("input can't be null");
        }
        return str.trim()
                .replaceAll(" ", "-")
                .toLowerCase();
    }

    public static List<String> generateSlugArray(String[] tags) {
        List<String> taglist = new ArrayList<>();
        if (tags != null) {
            for (String tag : tags) {
                String s = generateSlug(tag.toLowerCase());
                taglist.add(s);
            }
        }
        return taglist;
    }

}
