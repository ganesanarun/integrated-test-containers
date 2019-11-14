package com.thoughtworks.customer;

import com.google.common.net.MediaType;
import com.thoughtworks.movie.MovieRepresentation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = CustomerRatingUserJourneyTests.ContextInitializer.class)
class CustomerRatingUserJourneyTests {

  @Container
  static PostgreSQLContainer postgres = new PostgreSQLContainer()
      .withDatabaseName("customer")
      .withUsername("postgres")
      .withPassword("docker");

  @Container
  private static RatingServiceContainer rating = new RatingServiceContainer("com.thoughtworks/rating");

  @Container
  private static MockServerContainer movie = new MockServerContainer();

  @Autowired
  MockMvc mvc;

  @Autowired
  RestTemplate restTemplate;

  @Test
  void ableToRetrieveCustomerRatings() throws Exception {

    mvc.perform(post("/customers/bulk"))
        .andDo(result -> {
          final var response = restTemplate.postForEntity(
              String.format("%s%s", rating.getRatingUrl(), "/ratings"),
              new Rating((short) 4, "from tests", 1, "random_movie"),
              String.class);
          assertEquals("{ id: 1 }", response.getBody());
        }).andDo(result -> {
      new MockServerClient(movie.getContainerIpAddress(), movie.getServerPort())
          .when(HttpRequest.request())
          .respond(response()
              .withBody(new ObjectMapper()
                  .writeValueAsString(new MovieRepresentation("Batman rises", "1992")),
                  MediaType.JSON_UTF_8));
      mvc.perform(get("/customers/1/ratings"))
          .andExpect(status().isOk())
          .andExpect(finalResult -> {
            assertEquals("[{\"rating\":4,\"comments\":\"from tests\",\"customerId\":1,\"movieId\":\"random_movie\",\"title\":\"Batman rises\",\"year\":\"1992\"}]",
                finalResult.getResponse().getContentAsString());
          });
    });
  }

  static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      System.out.println("Rating url:" + rating.getRatingUrl());
      TestPropertyValues values = TestPropertyValues.of(
          Stream.of("customer.rating.url=" + rating.getRatingUrl(),
              "spring.datasource.url=" + postgres.getJdbcUrl(),
              "customer.movie.url=" + String.format("%s/?s=sample",movie.getEndpoint())));
      values.applyTo(applicationContext);
    }
  }

  private class Rating {

    private short rating;

    private String comments;

    private long customerId;

    private String movieId;

    private Rating(short rating, String comments, long customerId, String movieId) {
      this.rating = rating;
      this.comments = comments;
      this.customerId = customerId;
      this.movieId = movieId;
    }

    public short getRating() {
      return rating;
    }

    public String getComments() {
      return comments;
    }

    public long getCustomerId() {
      return customerId;
    }

    public String getMovieId() {
      return movieId;
    }
  }
}