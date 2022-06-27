package com.example.webtest.controller;

import com.example.webtest.model.Laptop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/laptops", Laptop[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        List<Laptop> laptops = Arrays.asList(response.getBody());
    }

    @Test
    void findOne() {
        Laptop laptop = new Laptop(1L, "My laptop");
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/laptops/3", Laptop.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findOneWithNegativeId() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/laptops/-1", Laptop.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteAll() {
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "name": "Laptop for test"
                }
        """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange(
                "/laptops", HttpMethod.POST, request, Laptop.class
        );
        Laptop laptop = response.getBody();
        System.out.println(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(1L, laptop.getId());
        assertEquals("Laptop for test", laptop.getName());
    }

    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "name": "Laptop for test"
                }
        """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange(
                "/laptops", HttpMethod.POST, request, Laptop.class
        );
        // update
        Laptop laptop = response.getBody();
        laptop.setName("Updated name");
        ObjectMapper obj = new ObjectMapper();
        String n = null;
        try {
            n = obj.writeValueAsString(laptop);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpEntity<String> request2 = new HttpEntity<>(n, headers);
        ResponseEntity<Laptop> response2 = testRestTemplate.exchange(
                "/laptops", HttpMethod.POST, request, Laptop.class
        );

        System.out.println(response2);
    }

    @Test
    void delete() {
    }
}