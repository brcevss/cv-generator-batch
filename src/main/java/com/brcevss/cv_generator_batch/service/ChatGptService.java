package com.brcevss.cv_generator_batch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.*;

@Service
public class ChatGptService {
    private static final String API_URL = "https://api.openai.com/v1/completions";

    @Value("${api.key}")
    private String API_KEY;

    public String generateText(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Création du corps de la requête
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 150);

        // Configuration des en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // Envoi de la requête
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        // Traitement de la réponse
        String responseBody = response.getBody();
        JSONObject jsonResponse = new JSONObject(responseBody);
        return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");
    }
}
