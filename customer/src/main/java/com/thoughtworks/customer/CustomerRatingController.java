package com.thoughtworks.customer;

import com.thoughtworks.movie.Movie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/customers/{customerId}")
public class CustomerRatingController {

  private final Movie movie;

  private final RestTemplate restTemplate;

  private final CustomerProperties customerProperties;

  public CustomerRatingController(RestTemplate restTemplate,
                                  CustomerProperties customerProperties,
                                  Movie movie) {
    this.restTemplate = restTemplate;
    this.customerProperties = customerProperties;
    this.movie = movie;
  }

  @GetMapping("/ratings")
  public List<CustomerRatingRepresentation> all(@PathVariable String customerId) throws URISyntaxException {
    final var exchange = restTemplate.exchange(new URI(customerProperties.getRating().getUrl() + "/ratings"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<RatingRepresentation>>() {
        });
    var ratings = exchange.getBody();
    return ratings.stream().map(ratingRepresentation -> {
      var aFor = movie.getFor(ratingRepresentation.getMovieId());
      return new CustomerRatingRepresentation(ratingRepresentation.getRating(),
          ratingRepresentation.getComments(),
          ratingRepresentation.getCustomerId(),
          ratingRepresentation.getMovieId(),
          aFor.get().getTitle(),
          aFor.get().getYear());
    }).collect(toList());
  }
}
