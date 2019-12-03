package com.xebia.fs101.writerpad.utilities;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilTest {

    @Test
    void should_generateSlug_of_a_string() {
        String input = " hello World ";
        String expectedOutput = StringUtil.generateSlug(input);
        assertThat(expectedOutput).isEqualTo("hello-world");
    }

    @Test
    void should_generateSlugSet_of_strings_from_a_String_array() {
        String[] input = new String[2];
        input[0] = " Hello world ";
        input[1] = " good Bye";
        List<String> expectedOutput = StringUtil.generateSlugArray(input);
        assertThat(expectedOutput).contains("hello-world", "good-bye");
    }

    @Test
    void should_convert_slugId_to_id() {
        UUID id = UUID.randomUUID();
        String slugId = "title1-"+id.toString();
        UUID expectedOutput = StringUtil.extractId(slugId);
        assertThat(expectedOutput).isEqualTo(id);
    }
}