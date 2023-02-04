package ru.fidarov.library.models;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Please enter the name")
    @Size(min = 1, max = 30, message = "Length must be 2-30 symbols")
    //make regex with ФИО;
    private String name;

    @Column(name = "ageOfBirth")
    @Min(value = 0, message = "age of birth must be more than 0")
    private int ageOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person() {

    }
    public Person(String name, int ageOfBirth) {
        this.name = name;
        this.ageOfBirth = ageOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgeOfBirth() {
        return ageOfBirth;
    }

    public void setAgeOfBirth(int ageOfBirth) {
        this.ageOfBirth = ageOfBirth;
    }


}
