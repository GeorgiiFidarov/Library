package ru.fidarov.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ru.fidarov.library.models.Person;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Page<Person> findAll(Pageable pageable);
    List<Person> findByNameLike(String search);

}
