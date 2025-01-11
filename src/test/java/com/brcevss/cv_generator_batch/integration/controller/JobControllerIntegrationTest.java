package com.brcevss.cv_generator_batch.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JobControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

/*    @Test
    public void testRunJob() {
        ResponseEntity<String> response = restTemplate.getForEntity("/run-job", String.class);
        assertEquals("Job lancé avec succès", response.getBody());
    }*/
}

