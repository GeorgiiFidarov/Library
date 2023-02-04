package ru.fidarov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fidarov.library.models.Book;
import ru.fidarov.library.models.Person;
import ru.fidarov.library.repositories.PersonRepository;
import ru.fidarov.library.services.BookServices;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookServices bookServices;
    private final PersonRepository personRepository;

    @Autowired
    public BookController(BookServices bookServices,
                          PersonRepository personRepository) {
        this.bookServices = bookServices;
        this.personRepository = personRepository;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(defaultValue = "0") String page,
                        @RequestParam(defaultValue = "10") String bookPerPage,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "null") String wordToSearch){
        if (wordToSearch.equals("null"))
            model.addAttribute("books",bookServices.findAll(
                    Integer.parseInt(page),
                    Integer.parseInt(bookPerPage),
                    sortBy));
        else
            model.addAttribute("books",bookServices.search(wordToSearch));
        return "books/index";
    }
    //почему-то модель @ModelAttribute не внедряет на view
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookServices.show(id));


        if (bookServices.getOwnerId(id)!=null){
            model.addAttribute("name", bookServices.getOwnerName(id));
        }else
            model.addAttribute("people", personRepository.findAll());
        //System.out.println(bookServices.getOwnerName(id));
//        if (bookServices.getOwnerId(id) != null){
//            model.addAttribute("owner", bookServices.getOwnerId(id).getId());
//        }else{
//            model.addAttribute("name", bookServices.getOwnerName(id));
//        }
        return "books/show";
    }

    @PostMapping("/{id}")
    public String makeAdmin(@ModelAttribute("person") Person person,
                            @PathVariable("id") int id){
        bookServices.setPersonToBook(id,person);
        //System.out.println(bookDAO.getOwnerName(id));
        return "redirect:/books/{id}";
    }
    @PostMapping("/{id}/unset")
    public String unSet(@PathVariable("id")int id){
        bookServices.unSetPerson((id));
        return "redirect:/books/{id}";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";
        bookServices.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("book", bookServices.show(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/edit";
        bookServices.update(book, id); //мы же по id изменяем
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookServices.delete(id);
        return "redirect:/books";
    }
}
