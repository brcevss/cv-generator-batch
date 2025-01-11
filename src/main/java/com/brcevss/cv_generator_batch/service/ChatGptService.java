package com.brcevss.cv_generator_batch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ChatGptService {

    private static final Logger log = LoggerFactory.getLogger(ChatGptService.class);
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${api.key}")
    private String API_KEY;

    public String generateText(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            log.info("Début de la génération de texte avec le prompt: {}", prompt);

            // Création du corps de la requête pour GPT-4
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4o-mini");
            requestBody.put("messages", new JSONArray()
                    .put(new JSONObject()
                            .put("role", "system")
                            .put("content", "You are a helpful assistant."))
                    .put(new JSONObject()
                            .put("role", "user")
                            .put("content", prompt))
            );
            requestBody.put("max_tokens", 50);
            requestBody.put("temperature", 0.7);

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

            String generatedText = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            log.info("Texte généré avec succès: {}", generatedText);
            return generatedText;

        } catch (Exception e) {
            log.error("Erreur lors de la génération de texte avec le prompt: {}", prompt, e);
            throw new RuntimeException("Erreur lors de la génération de texte", e);
        }
    }
}
