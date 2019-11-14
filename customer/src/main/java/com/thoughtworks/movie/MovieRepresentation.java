package com.thoughtworks.movie;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class MovieRepresentation {
  @JsonAlias({"Title", "title"})
  private String title;
  @JsonAlias({"Year", "year"})
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
