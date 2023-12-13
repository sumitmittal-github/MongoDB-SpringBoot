package com.sumit.service;

import com.sumit.collection.Person;
import com.sumit.repository.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getPersonByNameStartsWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    public String deletePersonById(Long id) {
        personRepository.deleteById(id);
        return "Delete person with id : " + id;
    }

    public List<Person> findPersonByAgeBetween(Integer minAge, Integer maxAge) {
        return personRepository.findPersonByAgeBetween(minAge, maxAge);
    }

    public Page<Person> searchPersons(String firstName, String lastName, String city, Integer minAge, Integer maxAge, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        // define Criteria and add fields if they are not null from the request
        List<Criteria> criteriaList = new ArrayList<>();
        if(firstName != null && !firstName.isEmpty())
            criteriaList.add(Criteria.where("firstName").regex(firstName, "i"));
        if(lastName != null && !lastName.isEmpty())
            criteriaList.add(Criteria.where("lastName").regex(lastName, "i"));
        if(city != null && !city.isEmpty())
            criteriaList.add(Criteria.where("addresses.city").regex(city, "i"));
        if(minAge != null && maxAge != null)
            criteriaList.add(Criteria.where("age").gte(minAge).lte(maxAge));

        // if we have any criteria then add it to the query
        Query query = new Query().with(pageable);
        if(!criteriaList.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));

        Page<Person> personPage = PageableExecutionUtils.getPage(
                                        mongoTemplate.find(query, Person.class),
                                        pageable,
                                        () -> mongoTemplate.count(query.skip(0).limit(0), Person.class)
                                  );
        return personPage;
    }

    public List<Document> getOldestPersonByCity() {
        // all the operations
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");

        // aggregate all the operations
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        // use mongoTemplate to fetch data based on the aggregation
        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }


    public List<Document> getPopulationByCity() {
        // all the operations
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("populationCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "populationCount");

        // all the projections
        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("City")
                .andExpression("populationCount").as("count")
                .andExclude("_id");

        // aggregate all the operations
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);

        // use mongoTemplate to fetch data based on the aggregation
        return mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
    }

}