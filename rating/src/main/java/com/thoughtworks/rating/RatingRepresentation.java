package com.thoughtworks.rating;

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

  public RatingRepresentation() {

  }

  public static RatingRepresentation from(Rating rating) {
    return new RatingRepresentation(rating.getRating(), rating.getComments(), rating.getCustomerId(), rating.getMovieId());
  }

  public short getRating() {
    return rating;
  }

  public void setRating(short rating) {
    this.rating = rating;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }
}
