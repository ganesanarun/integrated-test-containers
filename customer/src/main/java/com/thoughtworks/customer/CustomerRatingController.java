package com.thoughtworks.customer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}")
public class CustomerRatingController {

  RestTemplate restTemplate;

  CustomerProperties customerProperties;

  public CustomerRatingController(RestTemplate restTemplate, CustomerProperties customerProperties) {
    this.restTemplate = restTemplate;
    this.customerProperties = customerProperties;
  }

  @GetMapping("/ratings")
  public List<CustomerRatingRepresentation> all(@PathVariable String customerId) throws URISyntaxException {
    final var exchange = restTemplate.exchange(new URI(customerProperties.getRating().getUrl() + "/ratings"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<CustomerRatingRepresentation>>() {
        });
    return exchange.getBody();
  }
}
