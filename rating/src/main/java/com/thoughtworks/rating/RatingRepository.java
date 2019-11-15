package com.thoughtworks.rating;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface RatingRepository extends CrudRepository<Rating, Long> {

  @Query("FROM Rating r where r.customerId= :customerId")
  Iterable<Rating> findByCustomerId(@Param("customerId") long customerId);
}
