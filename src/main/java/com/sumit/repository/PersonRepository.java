package com.sumit.repository;


import com.sumit.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, Long> {

    List<Person> findByFirstNameStartsWith(String name);

    @Query(value = "{'age' : {$gt : ?0, $lt : ?1}}",
            fields = "{addresses : 0,  hobbies: 0}")
    List<Person> findPersonByAgeBetween(Integer minAge, Integer maxAge);
    // could also use : List<Person> findByAgeBetween(minAge, maxAge)
}