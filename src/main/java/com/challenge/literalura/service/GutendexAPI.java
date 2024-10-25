package com.challenge.literalura.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexAPI {
  private final String BASE_URL = "http://gutendex.com/books/";

  public String obtenerDatos(String params) {
    var url = BASE_URL + params;
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
//        .timeout(Duration.ofSeconds(10))
        .build();
    HttpResponse<String> response = null;
    try {
      response = client
          .send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println(response.statusCode()==200 ? "OK" : "Hay problemas");
    String json = response.body();
    return json;
  }
}
