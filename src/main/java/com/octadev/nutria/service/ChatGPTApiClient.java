package com.octadev.nutria.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octadev.nutria.models.ChatMessage;
import com.octadev.nutria.models.ChatRequest;

public class ChatGPTApiClient {

  private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
  private static final String OPENAI_API_KEY = "Bearer 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";

  public static String callChatGPTAPI(String prompt, String content) {
    try {
      // Cria o cliente HTTP
      CloseableHttpClient httpClient = HttpClientBuilder.create().build();

      // Cria o objeto de solicitação HTTP POST
      HttpPost request = new HttpPost(OPENAI_API_URL);

      // Define o cabeçalho de autorização com a chave da API
      request.setHeader(HttpHeaders.AUTHORIZATION, OPENAI_API_KEY);

      // Cria o objeto ObjectMapper
      ObjectMapper objectMapper = new ObjectMapper();

      List<Object> messages = new ArrayList<>();

      // Mensagem de sistema
      ChatMessage systemMessage = new ChatMessage("system", content);
      messages.add(systemMessage);

      // Mensagem do usuário
      ChatMessage userMessage = new ChatMessage("user", prompt);
      messages.add(userMessage);

      String jsonPayload = objectMapper.writeValueAsString(new ChatRequest(messages, "gpt-3.5-turbo"));
      StringEntity requestEntity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);

      // Define o corpo da solicitação
      request.setEntity(requestEntity);

      // Envia a solicitação e obtém a resposta
      CloseableHttpResponse response = httpClient.execute(request);
      HttpEntity responseEntity = response.getEntity();

      // Processa a resposta
      if (responseEntity != null) {
        return EntityUtils.toString(responseEntity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
