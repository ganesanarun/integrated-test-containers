package com.thoughtworks.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class RatingRepresentation {
  private short rating;

  private String comments;

  private long customerId;

  private String movieId;

  public RatingRepresentation(short rating, String comments, long customerId, String movieId) {
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

  public RatingRepresentation() {
  }

  public void setRating(short rating) {
    this.rating = rating;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  @Override
  public String toString() {
    return String.format(
        "{rating: %d, comments: %s, customer-id: %d, movie-id: %d}",
        rating,
        comments,
        customerId,
        movieId);
  }
}
