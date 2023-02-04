//package ru.fidarov.library.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import ru.fidarov.library.models.Book;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class BookDAO {
//    private final JdbcTemplate jdbcTemplate;
//    @Autowired
//    public BookDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    //all books from db
//    public List<Book> index(){
//        return jdbcTemplate.query("SELECT * FROM book ORDER BY book_id",
//                new BeanPropertyRowMapper<>(Book.class));
//    }
//    public Book show(int id){
//        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?",new Object[]{id},
//                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
//    }
//    public void save(Book book){
//        jdbcTemplate.update("INSERT INTO book(name,author,yearOfProduction,owner) VALUES (?, ?, ?, 0)",
//                book.getName(),book.getAuthor(),book.getYearOfProduction());
//    }
//    public void update(int id,Book updatedBook){
//        jdbcTemplate.update("UPDATE book Set name=?,author=?,yearOfProduction=? WHERE book_id=?",
//                updatedBook.getName(),updatedBook.getAuthor(),updatedBook.getYearOfProduction(),id);
//    }
//
//    public Object unSetPerson(int id){
//        jdbcTemplate.update("UPDATE book SET owner=0 WHERE book_id=?",id);
//        return null;
//    }
//    public int getOwnerId(int id) {
//        return jdbcTemplate.queryForObject("SELECT owner FROM book WHERE book_id=?",
//                new Object[]{id}, Integer.class);
//    }
//
//    public void delete(int id){
//        jdbcTemplate.update("DELETE from book WHERE book_id=?",id);
//    }
//    public String getOwnerName(int id){
//        return jdbcTemplate.queryForObject("select person.name from book join person on book.owner = person.id where book_id=?",
//                new Object[]{id},String.class);
//    }
//}
