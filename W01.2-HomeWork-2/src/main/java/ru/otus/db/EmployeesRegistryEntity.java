package ru.otus.db;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "emp_registry")
public class EmployeesRegistryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "firsh_name")
    private String firstName;

    @Basic
    @Column(name = "sur_name")
    private String surName;

    @ManyToOne
    @JoinColumn(name = "department", referencedColumnName = "id", nullable = false)
    private DepartmentsDirectoryEntity department;

    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "job")
    private String job;

    @Basic
    @Column(name = "salary")
    private Long salary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
