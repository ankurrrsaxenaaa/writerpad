package com.xebia.fs101.writerpad.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.writerpad.api.representations.UserRequest;
import com.xebia.fs101.writerpad.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void mockmvc_should_be_not_null() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void should_register_a_user_if_credentials_are_valid() throws Exception {
        UserRequest user = new UserRequest("ankursaxena26", "ankur.saxena@xebia.com", "123");
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_not_register_an_user_if_credentials_are_not_valid() throws Exception {
        UserRequest user = new UserRequest("", "ankur.saxena@xebia.com", "123");
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_register_an_user_if_credentials_are_same() throws Exception {
        UserRequest user1 = new UserRequest("ankursaxena26", "ankur.saxena@xebia.com", "123");
        UserRequest user2 = new UserRequest("ankursaxena2605", "ankur.saxena@xebia.com", "123");
        String json1 = objectMapper.writeValueAsString(user1);
        String json2 = objectMapper.writeValueAsString(user2);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andDo(print())
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}