package com.timo.spring.dao;

import com.timo.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query(
                "SELECT * FROM person",
                    new BeanPropertyRowMapper<>(Person.class)
        );
    }

    public Optional<Person> getPerson(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM person WHERE id=?",
                    new Object[]{id},
                    new BeanPropertyRowMapper<>(Person.class)
        ).stream().findAny();
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, birthyear) VALUES (?, ?)",
                person.getFullName(),
                person.getBirthYear());
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, birthyear=? WHERE id=?",
                person.getFullName(),
                person.getBirthYear(),
                id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?",
                id);
    }
}
