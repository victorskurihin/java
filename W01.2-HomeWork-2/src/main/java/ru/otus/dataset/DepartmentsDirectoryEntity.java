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
    private long parent_id; // recursion

    @Basic
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<EmployeesRegistryEntity> employees = new HashSet<EmployeesRegistryEntity>();
}
