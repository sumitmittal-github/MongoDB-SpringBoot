package com.sumit.controller;

import com.sumit.collection.Person;
import com.sumit.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public ResponseEntity<List<Person>> getAllPerson(){
        List<Person> list = personService.getAllPerson();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Person> savePerson(@RequestBody Person person){
        person = personService.savePerson(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Person>> getPersonByNameStartsWith(@RequestParam("name") String name){
        List<Person> list = personService.getPersonByNameStartsWith(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonById(@PathParam("id") Long id){
        String response = personService.deletePersonById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseEntity<List<Person>> getPersonByAgeRange(@RequestParam("minAge") Integer minAge, @RequestParam("maxAge") Integer maxAge){
        List<Person> list = personService.findPersonByAgeBetween(minAge, maxAge);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Person>> searchPersons(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "minAge", required = false) Integer minAge,
            @RequestParam(value = "maxAge", required = false) Integer maxAge,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Page<Person> list = personService.searchPersons(firstName, lastName, city, minAge, maxAge, page, size);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/oldestPersonByCity")
    public ResponseEntity<List<Document>> getOldestPersonByCity(){
        List<Document> documents = personService.getOldestPersonByCity();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/populationByCity")
    public ResponseEntity<List<Document>> getPopulationByCity(){
        List<Document> documents = personService.getPopulationByCity();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
}