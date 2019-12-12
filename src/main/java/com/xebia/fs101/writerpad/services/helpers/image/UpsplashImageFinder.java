package com.xebia.fs101.writerpad.services.helpers.image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Profile("!test")
public class UpsplashImageFinder implements ImageFinder {

    @Value("${upsplash.accesstoken}")
    String accessToken;

    @Override
    public String findRandomImage() {
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(
                "https://api.unsplash.com/photos/random?client_id=" + accessToken,
                String.class);
        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readTree(Objects.requireNonNull(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (jsonNode != null) {
            return jsonNode.get("urls").get("regular").toString();
        }
        return "image not available";
    }

    public static void main(String[] args) {
        UpsplashImageFinder a = new UpsplashImageFinder();
        System.out.println("a = " + a.findRandomImage());
    }
}
