package ru.otus;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "registry")
public class RegistryEntity implements Serializable {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "firsh_name")
    private String firstName;

    @Basic
    @Column(name = "sur_name")
    private String surName;
}
