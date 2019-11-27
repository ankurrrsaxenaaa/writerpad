package com.xebia.fs101.writerpad.utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class StringUtil {

    public StringUtil() {
    }

    public static String generateSlug(String str) {
        return str.trim().replaceAll(" ", "-").toLowerCase();
    }


    public static List<String> generateSlugArray(String[] tags) {
        List<String> taglist = new ArrayList<>();
        for (String tag : tags) {
            String toLowerCase = tag.toLowerCase();
            String s = generateSlug(toLowerCase);
            taglist.add(s);
        }
        return taglist;
    }

}
