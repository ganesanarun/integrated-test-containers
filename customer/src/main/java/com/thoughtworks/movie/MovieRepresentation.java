package com.thoughtworks.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class MovieRepresentation {
  @JsonProperty("Title")
  private String title;
  @JsonProperty("Year")
  private String year;

  public MovieRepresentation() {
  }

  public MovieRepresentation(String title, String year) {
    this.title = title;
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return String.format(
        "{title: %s, year: %s}",
        title,
        year);
  }
}
