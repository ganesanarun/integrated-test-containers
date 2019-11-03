package com.thoughtworks.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RatingRepository extends CrudRepository<Rating, Long> {

}
