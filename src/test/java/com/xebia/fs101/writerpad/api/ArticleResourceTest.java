package com.xebia.fs101.writerpad.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mock_mvc_should_be_set() {
        Assertions.assertThat(mockMvc).isNotNull();
    }


    @Test
    void check_api_articles_post_request_status_as_created() throws Exception {

        String mockJson = "{\n" +
                "  \"title\": \"How to learn Spring Booot\",\n" +
                "  \"description\": \"Ever wonder how?\",\n" +
                "  \"body\": \"You have to believe\",\n" +
                "  \"tags\": [\"java\", \"Spring Boot\", \"tutorial\"],\n" +
                "  \"featuredImage\": \"url of the featured image\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/articles")
                .content(mockJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void check_api_articles_post_request_status_as_bad_request() throws Exception {

        String mockJson = "{\n" +
                "  \"description\": \"Ever wonder how?\",\n" +
                "  \"body\": \"You have to believe\",\n" +
                "  \"tags\": [\"java\", \"Spring Boot\", \"tutorial\"],\n" +
                "  \"featuredImage\": \"url of the featured image\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/articles")
                .content(mockJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}