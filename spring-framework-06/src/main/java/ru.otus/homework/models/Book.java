package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book implements Serializable, DataSet
{
    private long id;

    private String isbn;

    private String title;

    private int editionNumber;

    private String copyright;

    private Publisher publisher;

    private Genre genre;
}
