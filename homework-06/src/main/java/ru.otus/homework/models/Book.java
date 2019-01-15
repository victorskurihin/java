package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Book implements Serializable, DataSet
{
    static final long serialVersionUID = -2L;

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "edition_number")
    private int editionNumber;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "genre_id")
    private Genre genre;
}
