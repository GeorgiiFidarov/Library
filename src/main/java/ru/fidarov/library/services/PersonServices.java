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

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonServices {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonServices(PersonRepository personRepository,
                          BookRepository bookRepository)
    {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> findAll(int page,int personPerPage,String sortBy)
    {
        Page<Person> peoplePage = personRepository.findAll(PageRequest.of(page,personPerPage, Sort.by(sortBy)));
        return peoplePage.getContent();
    }
    public List<Person> search(String name)
    {
        return personRepository.findByNameLike("%"+name+"%");
    }
    public Person show(int id)
    {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Person person)
    {
        personRepository.save(person);
    }
//    @Transactional
//    public Object saveALot(){
//        List<String> names = Arrays.asList("Anastasia","Angelina","Alan","Sos","Anton","Sergey");
//        List<String> surnames = Arrays.asList("Damiano","Hubetsev","Demina","Iglesias","Fidarov","Kudzaev");
//        List<Integer> ages = Arrays.asList(1995,1965,1978,1965,1965,1964,1965);
//        Random random = new Random();
//        for (int i = 0; i<100;i++){
//            String name = names.get(random.nextInt(names.size()));
//            String surname = surnames.get(random.nextInt(surnames.size()));
//            int age = ages.get(random.nextInt(ages.size()));
//            personRepository.save(new Person(name+" "+surname,age));
//        }
//        return null;
//    }
    @Transactional
    public void update(Person updatedPerson,int id)
    {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id)
    {
        personRepository.deleteById(id);
    }

    public List<Book> getSetOfOwners(int id)
    {
        return bookRepository.findAllBooksByOwnerId(id);
    }


}
