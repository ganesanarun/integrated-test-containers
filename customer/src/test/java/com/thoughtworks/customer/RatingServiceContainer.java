package com.thoughtworks.customer;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

class RatingServiceContainer extends GenericContainer<RatingServiceContainer> {

  private final PostgreSQLContainer postgres;

  public RatingServiceContainer(String dockerImageName) {
    super(dockerImageName);
    postgres = new PostgreSQLContainer()
        .withDatabaseName("postgres")
        .withUsername("postgres")
        .withPassword("docker");
  }

  @Override
  protected void configure() {
    postgres.start();
    addExposedPort(8081);
    withEnv("spring.datasource.url", String.format("jdbc:postgresql://host.docker.internal:%d/postgres",
        postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)));
    waitingFor(Wait.forHttp("/ratings"));
  }

  public String getRatingUrl() {
    return String.format("http://%s:%d",
        this.getContainerIpAddress(),
        this.getMappedPort(8081));
  }
}
