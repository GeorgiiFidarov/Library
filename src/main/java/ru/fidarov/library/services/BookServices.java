package ru.fidarov.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fidarov.library.models.Book;
import ru.fidarov.library.models.Person;
import ru.fidarov.library.repositories.BookRepository;
import ru.fidarov.library.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class BookServices {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookServices(BookRepository bookRepository,
                        PersonRepository personRepository)
    {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }
    public List<Book> findAll(int page,int bookPerPage,String sortBy)
    {
        Page<Book> peoplePage = bookRepository.findAll(PageRequest.of(page,bookPerPage, Sort.by(sortBy)));
        return peoplePage.getContent();
    }
    public List<Book> search(String name)
    {
        return bookRepository.findByNameLike("%"+name+"%");
    }
    public Book show(int id)
    {
        Optional<Book>foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }
    @Transactional
    public void save(Book book)
    {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id)
    {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id)
    {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setPersonToBook(int id, Person person)
    {
        Optional<Book> bookToBeOwned = bookRepository.findById(id);//находим книгу которую мы присвоим
        assert bookToBeOwned.orElse(null) != null;
        bookToBeOwned.orElse(null).setOwner(person);
        bookToBeOwned.orElse(null).setCheckedIn(new Date());
    }
    public Person getOwnerId(int id)
    {
        Optional<Book> ownedBook = bookRepository.findById(id);
        assert ownedBook.orElse(null) != null;
        return ownedBook.orElse(null).getOwner();
    }

    public String getOwnerName(int id)
    {
        Optional<Book> ownedBook = bookRepository.findById(id);
        assert ownedBook.orElse(null) != null;
        return ownedBook.orElse(null).getOwner().getName();
    }
    @Transactional
    public void unSetPerson(int id)
    {
        Optional<Book> bookToFree = bookRepository.findById(id);
        assert bookToFree.orElse(null) != null;
        bookToFree.orElse(null).setOwner(null);
    }
}
