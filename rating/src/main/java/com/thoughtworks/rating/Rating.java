package com.thoughtworks.rating;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rating")
public class Rating implements Serializable {
  private static final long serialVersionUID = 5846042052405656442L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long Id;

  @Column(name = "customerid")
  private long customerId;

  @Column(name = "movieid")
  private long movieId;

  private short rating;

  @Column
  private String comments;

  public Rating(long customerId, long movieId, short rating, String comments) {
    this.customerId = customerId;
    this.movieId = movieId;
    this.rating = rating;
    this.comments = comments;
  }

  public Rating() {
  }

  public long getId() {
    return Id;
  }

  public void setId(long id) {
    Id = id;
  }

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
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
}
