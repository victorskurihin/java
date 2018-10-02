package ru.otus;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "login")
    private String login;

    @Basic
    @Column(name = "password")
    private String password;
}
