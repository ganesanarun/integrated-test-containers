package com.thoughtworks.customer;

public class CustomerRatingRepresentation {
  private short rating;

  private String comments;

  private long customerId;

  private String movieId;

  private String title;

  private String year;

  public CustomerRatingRepresentation(short rating, String comments, long customerId, String movieId, String title, String year) {
    this.rating = rating;
    this.comments = comments;
    this.customerId = customerId;
    this.movieId = movieId;
    this.title = title;
    this.year = year;
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

  public String getTitle() {
    return title;
  }

  public String getYear() {
    return year;
  }
}
