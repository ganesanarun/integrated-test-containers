package com.thoughtworks.movie;


import com.thoughtworks.customer.CustomerProperties;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;

public class Movie {

  private final CustomerProperties movieService;
  private final RestTemplate restTemplate;

  public Movie(CustomerProperties movieService, RestTemplate restTemplate) {
    this.movieService = movieService;
    this.restTemplate = restTemplate;
  }

  public Optional<MovieRepresentation> getFor(String id) {
    try {
      System.out.println("========================");
      System.out.println(new URI(movieService.getMovie().getUrl() + String.format("&i=%s", id)));
      System.out.println("========================");
      final var exchange = restTemplate.exchange(
          new URI(movieService.getMovie().getUrl() + String.format("&i=%s", id)),
          HttpMethod.GET,
          null,
          MovieRepresentation.class);
      System.out.println(exchange.getBody());
      return Optional.ofNullable(exchange.getBody());
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      System.out.println(Arrays.toString(exception.getStackTrace()));
      return Optional.of(new MovieRepresentation("<<No entry>>", "<<No entry>>"));
    }
  }
}
