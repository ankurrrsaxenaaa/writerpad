package com.xebia.fs101.writerpad.utilities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class StringUtilTest {

    @Test
    void should_generateSlug_of_a_string() {
        String input = " hello World ";
        String expectedOutput = StringUtil.generateSlug(input);
        Assertions.assertThat(expectedOutput).isEqualTo("hello-world");
    }

    @Test
    void should_generateSlugSet_of_strings_from_a_String_array() {
        String[] input = new String[2];
        input[0]=" Hello world ";
        input[1]=" good Bye";
        Set<String> expectedOutput = StringUtil.generateSlugArray(input);
        Assertions.assertThat(expectedOutput).contains("hello-world", "good-bye");
    }
}