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
@Table(name = "book_review")
public class Review implements Serializable, DataSet
{
    static final long serialVersionUID = -3L;

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String review;

    /* @ManyToOne(optional = false, cascade = CascadeType.ALL)
       @JoinColumn(name = "book_id")
    // unidirectional private Book book; */
}
