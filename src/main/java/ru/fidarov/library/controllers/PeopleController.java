package ru.fidarov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fidarov.library.models.Person;
import ru.fidarov.library.services.PersonServices;

import javax.validation.Valid;
import java.text.ParseException;



@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonServices personServices;

    @Autowired
    public PeopleController(PersonServices personServices) {
        this.personServices = personServices;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(defaultValue = "0") String page,
                        @RequestParam(defaultValue = "50") String personPerPage,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "null") String wordToSearch) {

        if (wordToSearch.equals("null"))
            model.addAttribute("people",personServices.findAll(
                    Integer.parseInt(page),
                    Integer.parseInt(personPerPage),
                    sortBy));
        else
            model.addAttribute("people",personServices.search(wordToSearch));
        return "people/index";
    }

    //personal page for each of them
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,@ModelAttribute("person") Person person) throws ParseException {
        model.addAttribute("person", personServices.show(id));
        model.addAttribute("checks",personServices.checkDeadLine(id));

        //System.out.println(model.addAttribute("checks",personServices.checkDeadLine(id)));

        if (personServices.checkDeadLine(id).size()==0){
            model.addAttribute("checks",personServices.checkDeadLine(id));
        }
        else {
            model.addAttribute("empty");
        }
        return "people/show";
    }
    //this will get us a page with adding a person
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person)
    {
        return "people/new";
    }
    //and here we post into DAO and DAO to db

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return "people/new";
        personServices.save(person);
        //personServices.saveALot();
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id)
    {
        model.addAttribute("person", personServices.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         @PathVariable("id") int id, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return "people/edit";
        personServices.update(person, id);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id)
    {
        personServices.delete(id);
        return "redirect:/people";
    }
}
