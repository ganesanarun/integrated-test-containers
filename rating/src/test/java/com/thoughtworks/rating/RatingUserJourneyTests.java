package com.thoughtworks.rating;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = RatingUserJourneyTests.ContextInitializer.class)
public class RatingUserJourneyTests {

  @Container
  static PostgreSQLContainer postgres = new PostgreSQLContainer()
      .withDatabaseName("postgres")
      .withUsername("postgres")
      .withPassword("docker");
  @Autowired
  MockMvc mvc;

  @Test
  void ableToReturnCustomers() throws Exception {
    final RatingRepresentation interesting = new RatingRepresentation(
        (short)4,
        "interesting",
        1,
        "1");
    mvc.perform(post("/ratings")
        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(interesting)))
        .andExpect(status().isOk());
    mvc.perform(get("/ratings"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(result -> {
          System.out.println(result.getResponse().getContentAsString());
          var expectedResponse = "[{\"rating\":4,\"comments\":\"interesting\",\"customerId\":1,\"movieId\":\"1\"}]";
          assertEquals(expectedResponse, result.getResponse().getContentAsString());
        });
  }

  static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertyValues values = TestPropertyValues.of(
          "spring.datasource.url=" + postgres.getJdbcUrl()
      );
      values.applyTo(applicationContext);
    }
  }
}
