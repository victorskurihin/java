package ru.otus;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "directory")
public class DirectoryEntity implements Serializable {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "pid")
    private String parent_id;

    @Basic
    @Column(name = "title")
    private String title;
}
