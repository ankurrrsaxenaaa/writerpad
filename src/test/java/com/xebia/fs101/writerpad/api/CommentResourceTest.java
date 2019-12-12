package com.xebia.fs101.writerpad.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.writerpad.api.representations.CommentRequest;
import com.xebia.fs101.writerpad.api.representations.UserRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.Comment;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.domain.WriterpadRole;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.CommentRepository;
import com.xebia.fs101.writerpad.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class CommentResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private User user;

    private String getSlugUuid(Article saved) {
        return saved.getTitle() + "-" + saved.getId();
    }

    private Article getArticle() {
        return new Article.Builder()
                .setBody("Life is beautiful")
                .setTitle("Great world")
                .setDescription("Yeah it is beautiful")
                .setTags(Arrays.asList("okay", "nice", "yups"))
                .build();
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        UserRequest userRequest = new UserRequest(
                "ankursaxena",
                "ankur.saxena@xebia.com",
                "p@ssw0rd",
                WriterpadRole.WRITER);
        user = userRequest.toUser(passwordEncoder);
        userRepository.save(user);
    }

    @Test
    void mock_mvc_not_null() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void should_be_able_to_return_201_status_for_post_ops_when_comment_has_mandatory_data() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        String json = objectMapper.writeValueAsString(new CommentRequest("Beautiful comment"));
        Article saved = articleRepository.save(article);
        String id = getSlugUuid(saved);
        mockMvc.perform(post("/api/articles/{slugUuid}/comments", id)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_be_able_to_return_400_status_for_post_ops_when_comment_is_blank() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        String json = objectMapper.writeValueAsString(new CommentRequest(""));
        Article saved = articleRepository.save(article);
        String id = getSlugUuid(saved);
        mockMvc.perform(post("/api/articles/{slugUuid}/comments", id)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_be_able_to_return_404_status_for_post_ops_when_article_is_not_found() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        String json = objectMapper.writeValueAsString(new CommentRequest("Yeah"));
        Article saved = articleRepository.save(article);
        String id = getSlugUuid(saved) + "abc";
        mockMvc.perform(post("/api/articles/{slugUuid}/comments", id)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_400_status_for_post_ops_when_body_data_contain_spam_words() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        Article saved = articleRepository.save(article);
        String slugUuid = getSlugUuid(saved);
        CommentRequest request = new CommentRequest("Hello world! semen");
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/articles/{slugUuid}/comments", slugUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_be_able_to_return_201_status_and_all_comments() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        Comment comment1 = new Comment.Builder()
                .setArticle(article)
                .setBody("yeah1")
                .build();
        comment1.setUpdatedAt();
        Comment comment2 = new Comment.Builder()
                .setArticle(article)
                .setBody("yeah2")
                .build();
        comment2.setUpdatedAt();
        Article saved = articleRepository.save(article);
        commentRepository.saveAll(Arrays.asList(comment1, comment2));
        String id = getSlugUuid(saved);
        mockMvc.perform(get("/api/articles/{slugUuid}/comments", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_be_able_to_return_404_status_for_get_ops_when_article_is_not_present() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        Article saved = articleRepository.save(article);
        String id = "great-world-" + saved.getId() + "abc";
        mockMvc.perform(get("/api/articles/{slugUuid}/comments", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_be_able_to_return_404_status_for_delete_operation_when_article_is_not_present() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        Article saved = articleRepository.save(article);
        String id = "great-world-" + saved.getId() + "abc";
        mockMvc.perform(delete("/api/articles/{slugUuid}/comments/{id}", id, 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_be_able_to_return_204_status_for_delete_operation() throws Exception {
        Article article = getArticle();
        article.setUser(user);
        Article saved = articleRepository.save(article);
        Comment comment1 = new Comment.Builder()
                .setArticle(saved)
                .setBody("yeah1")
                .build();
        comment1.setUpdatedAt();
        Comment comment2 = new Comment.Builder()
                .setArticle(saved)
                .setBody("yeah2")
                .build();
        comment2.setUpdatedAt();
        commentRepository.saveAll(Arrays.asList(comment1, comment2));
        String uuid = getSlugUuid(saved);
        mockMvc.perform(delete("/api/articles/{slugUuid}/comments/{id}", uuid, comment1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}