package ru.otus.dataset;

import lombok.Data;
import ru.otus.dataset.EmployeesRegistryEntity;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "login")
    private String login;

    @Basic
    @Column(name = "password")
    private String password;
}
