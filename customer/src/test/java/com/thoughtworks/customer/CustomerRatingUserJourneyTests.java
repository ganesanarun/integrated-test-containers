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
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = CustomerRatingUserJourneyTests.ContextInitializer.class)
class CustomerRatingUserJourneyTests {

  @Container
  private static RatingServiceContainer rating = new RatingServiceContainer("com.thoughtworks/rating");

  @Autowired
  MockMvc mvc;

  @Test
  void ableToRetrieveCustomerRatings() throws Exception {
    mvc.perform(get("/customers/1/ratings"))
        .andExpect(status().isOk());
  }

  static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      System.out.println("Rating url:" + rating.getRatingUrl());
      TestPropertyValues values = TestPropertyValues.of(
          "customer.rating.url=" + rating.getRatingUrl()
      );
      values.applyTo(applicationContext);
    }
  }
}