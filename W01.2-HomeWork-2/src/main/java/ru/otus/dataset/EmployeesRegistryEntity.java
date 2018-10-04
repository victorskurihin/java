package ru.otus.dataset;

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
    @Column(name = "firsh_name", nullable = false)
    private String firstName;

    @Basic
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Basic
    @Column(name = "sur_name", nullable = false)
    private String surName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department", referencedColumnName = "id")
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
