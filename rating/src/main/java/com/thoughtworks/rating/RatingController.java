package com.thoughtworks.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

  @Autowired RatingRepository repository;

  @GetMapping
  public List<? extends RatingRepresentation> all() {
    final List<RatingRepresentation> ratingRepresentations = new ArrayList<>();
    repository.findAll().forEach(rating -> {
      ratingRepresentations.add(RatingRepresentation.from(rating));
    });
    return ratingRepresentations;
  }

  @PostMapping
  public String provide(@RequestBody RatingRepresentation ratingRepresentation) {
    Rating id = repository.save(new Rating(ratingRepresentation.getCustomerId(), ratingRepresentation.getMovieId(),
        ratingRepresentation.getRating(), ratingRepresentation.getComments()));
    return String.format("{ id: %s }", id.getId());

  }
}
