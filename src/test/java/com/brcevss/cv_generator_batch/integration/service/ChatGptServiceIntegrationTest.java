package com.brcevss.cv_generator_batch.integration.service;

import com.brcevss.cv_generator_batch.service.ChatGptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatGptServiceIntegrationTest {

    @Autowired
    private ChatGptService chatGptService;

/*    @Test
    public void testGenerateText() {
        String prompt = "Generate a resume";
        String generatedText = chatGptService.generateText(prompt);
        assertNotNull(generatedText);
    }*/
}

