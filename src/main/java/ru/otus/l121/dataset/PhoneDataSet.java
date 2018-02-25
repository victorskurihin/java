package ru.otus.l121.dataset;

import javax.persistence.*;

/**
 * TODO
 */
@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {
    @Column(name = "number", unique = true)
    private String number;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDataSet userDataSet;

    //Important for Hibernate
    public PhoneDataSet() {
        super(-1);
    }
    public PhoneDataSet(long id, String number) {
        super(id);
        this.number = number;
    }
    public PhoneDataSet(String number) {
        this(-1, number);
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUserDataSet() {
        return userDataSet;
    }
    public void setUserDataSet(UserDataSet userDataSet) {
        this.userDataSet= userDataSet;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
               " number='" + number + "'" +
               " }";
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return number.equals(that.number);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
