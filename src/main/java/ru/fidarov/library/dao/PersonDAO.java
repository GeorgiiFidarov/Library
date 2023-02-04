//package ru.fidarov.library.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import ru.fidarov.library.models.Book;
//import ru.fidarov.library.models.Person;
//
//import java.util.List;
//
//
//
//@Component
//public class PersonDAO {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    //вытаскиваем из базы таблицу с людьми
//    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM Person ORDER BY id",
//                new BeanPropertyRowMapper<>(Person.class));
//    }
//    public Person show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
//                new Object[]{id},//this inserts id value into our sqlQuery
//                //rowMapper returns us a list here we take only one Person value
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
//    }
//    public void save(Person person) {
//        jdbcTemplate.update("INSERT INTO Person(name,ageOfBirth) VALUES(?, ?)",
//                person.getName(),person.getAgeOfBirth());
//
//    }
//    public void setPersonToBook(int id,Person person){
//        jdbcTemplate.update("UPDATE book SET owner=? WHERE book_id=?",person.getId(),id);
//    }
//    public void update(int id, Person updatedPerson) {
//        jdbcTemplate.update("UPDATE Person SET name=?, ageOfBirth=? WHERE id=?",
//                updatedPerson.getName(),updatedPerson.getAgeOfBirth(),id);
//    }
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
//    }
//    public List<Book> getSetOfBooksToPerson(int id){
//        return jdbcTemplate.query("select book.name, book.author, book.yearofproduction from book join person on book.owner = person.id where id=? order by book_id",
//                new BeanPropertyRowMapper<>(Book.class),id);
//    }
//}
