package org.example.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerITest {
    @Autowired
    private TestRestTemplate template;

    @Test
    void index() {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    }

    @Test
    void hello() {
        ResponseEntity<String> response = template.getForEntity("/hello", String.class);
        assertThat(response.getBody()).isEqualTo("Hello, World!");
    }

    @Test
    void helloName() {
        String name = UUID.randomUUID().toString();
        ResponseEntity<String> response = template.getForEntity("/hello?name=" + name, String.class);
        assertThat(response.getBody()).isEqualTo(String.format("Hello, %s!", name));
    }
}