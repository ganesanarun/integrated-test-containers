package com.thoughtworks.customer;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Component
@ConfigurationProperties("customer")
public class CustomerProperties {

  @NestedConfigurationProperty
  @Valid
  private ServiceUrlProperties rating;

  public CustomerProperties() {
  }

  public void setRating(ServiceUrlProperties rating) {
    this.rating = rating;
  }

  public ServiceUrlProperties getRating() {
    return rating;
  }

  public static class ServiceUrlProperties {
    @URL
    private String url;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public ServiceUrlProperties() {
    }
  }
}
