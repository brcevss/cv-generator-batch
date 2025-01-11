package com.brcevss.cv_generator_batch.unit.service;

import com.brcevss.cv_generator_batch.service.ChatGptService;

import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.web.client.RestTemplate;

public class ChatGptServiceTest {

    @InjectMocks
    private ChatGptService chatGptService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*    @Test
    public void testGenerateText() {
        String prompt = "Generate a resume";
        String expectedResponse = "{\"choices\":[{\"message\":{\"content\":\"Generated resume\"}}]}";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, org.springframework.http.HttpStatus.OK));

        String result = chatGptService.generateText(prompt);
        assertEquals("Generated resume", result);

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }*/
}

