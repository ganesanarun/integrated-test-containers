package com.thoughtworks.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/ratings")
public class RatingController {

  @Autowired
  RatingRepository repository;

  private static <T> Stream<T> getStreamFromIterator(Spliterator<T> iterator) {
    return StreamSupport.stream(iterator, false);
  }

  @GetMapping
  public List<? extends RatingRepresentation> all(@RequestParam(defaultValue = "0", required = false) long customerId) {
    if (customerId == 0) {
      return getStreamFromIterator(repository.findAll().spliterator())
          .map(RatingRepresentation::from)
          .collect(toList());
    }
    return getStreamFromIterator(repository.findByCustomerId(customerId).spliterator())
        .map(RatingRepresentation::from)
        .collect(toList());
  }

  @PostMapping
  public String provide(@RequestBody RatingRepresentation ratingRepresentation) {
    Rating id = repository.save(new Rating(ratingRepresentation.getCustomerId(), ratingRepresentation.getMovieId(),
        ratingRepresentation.getRating(), ratingRepresentation.getComments()));
    return String.format("{ id: %s }", id.getId());
  }
}
