package ru.otus.dataset;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "dep_directory")
public class DepartmentsDirectoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "pid")
    private long parentId; // recursion

    @Basic
    @Column(name = "title")
    private String title;
}
