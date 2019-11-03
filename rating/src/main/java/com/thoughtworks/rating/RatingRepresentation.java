package com.thoughtworks.rating;

public class RatingRepresentation {

  private short rating;

  private String comments;

  private long customerId;

  private long movieId;

  public short getRating() {
    return rating;
  }

  public String getComments() {
    return comments;
  }

  public long getCustomerId() {
    return customerId;
  }

  public long getMovieId() {
    return movieId;
  }


  public RatingRepresentation(short rating, String comments, long customerId, long movieId) {
    this.rating = rating;
    this.comments = comments;
    this.customerId = customerId;
    this.movieId = movieId;
  }

  public static RatingRepresentation from(Rating rating) {
    return new RatingRepresentation(rating.getRating(), rating.getComments(), rating.getCustomerId(), rating.getMovieId());
  }
}
