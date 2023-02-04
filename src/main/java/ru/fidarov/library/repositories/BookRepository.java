package ru.fidarov.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fidarov.library.models.Book;
import ru.fidarov.library.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllBooksByOwnerId(int id);
    Page<Book> findAll(Pageable pageable);
    List<Book> findByNameLike(String search);
}
