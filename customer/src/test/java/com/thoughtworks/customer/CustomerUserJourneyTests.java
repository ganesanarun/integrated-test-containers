package com.thoughtworks.customer;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = CustomerUserJourneyTests.ContextInitializer.class)
public class CustomerUserJourneyTests {

  @Container
  static PostgreSQLContainer postgres = new PostgreSQLContainer()
      .withDatabaseName("customer")
      .withUsername("postgres")
      .withPassword("docker");

  @Autowired
  MockMvc mvc;

  @Test
  void ableToReturnCustomers() throws Exception {
    final MediaType mediaType = MediaType.parseMediaType("application/json");
    mvc.perform(post("/customers/bulk"))
        .andExpect(status().isOk());
    mvc.perform(get("/customers"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(mediaType))
        .andExpect(result -> {
          System.out.println(result.getResponse().getContentAsString());
          var expectedResponse = "[{\"firstName\":\"Steve\",\"lastName\":\"Smith\"},{\"firstName\":\"Rahul\",\"lastName\":\"Dravid\"},{\"firstName\":\"ABD\",\"lastName\":\"V\"}]";
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
