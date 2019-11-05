package com.thoughtworks.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = CustomerRatingUserJourneyTests.ContextInitializer.class)
public class CustomerRatingUserJourneyTests {

  @Container
  static GenericContainer rating = new GenericContainer("com.thoughtworks/rating")
      .withExposedPorts(8081);

  @Autowired
  MockMvc mvc;

  @Test
  public void ableToRetrieveCustomerRatings() throws Exception {
    mvc.perform(get("/customers/1/ratings"))
        .andExpect(status().isOk());
  }

  static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      System.out.println("Rating url:" + String.format("http://localhost:%d/", rating.getMappedPort(8081)));
      TestPropertyValues values = TestPropertyValues.of(
          "customer.rating.url=" + String.format("http://localhost:%d/", rating.getMappedPort(8081))
      );
      values.applyTo(applicationContext);
    }
  }
}
