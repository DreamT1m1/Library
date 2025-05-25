package com.timo.spring.controllers;

import com.timo.spring.models.Person;
import com.timo.spring.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", peopleService.getAllPeople());
        return "people/all_people";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id") int id, Model model) {
        Person person = peopleService.getPerson(id).get();
        model.addAttribute("person", person);
        System.out.printf("Person with id %d is shown\n", person.getId());
        return "people/show_person";
    }

    @GetMapping("/new_person")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new_person";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/new_person";
        }

        System.out.printf("New person with id %d is made. Hi, %s\n", person.getId(), person.getFullName());
        peopleService.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id).get());
        return "people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute @Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit_person";
        }

        System.out.printf("New person with id %d is updated\n", person.getId());
        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        System.out.printf("Person with id %d is deleted\n", id);
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
