package com.xebia.fs101.writerpad.utilities;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtil {

    public static String generateSlug(String str) {
        return str.trim().replaceAll(" ", "-").toLowerCase();
    }


    public static Set<String> generateSlugArray(String[] tags) {
        return Arrays.stream(tags).map(String::toLowerCase).map(StringUtil::generateSlug).collect(Collectors.toSet());
    }

}
