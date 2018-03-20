package ru.otus.l131.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

import javax.persistence.*;

/**
 * The class contains information about the user's address. This class is
 * associated with the UserDataSet class by the relationship of one to one.
 * This class is marked with an annotation for working with Hibernate ORM.
 */
@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {
    // for store the user's address as a street
    @Column(name = "street", unique = true)
    private String street;

    //Important for Hibernate
    public AddressDataSet() {
        super(-1);
    }
    public AddressDataSet(long id, String street) {
        super(id);
        this.street = street;
    }
    public AddressDataSet(String street) {
        this(-1, street);
    }

    public String getStreet () {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
               " street='" + street + "'" +
               " }";
    }

    @Override
    public int hashCode() {
        return street.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet that = (AddressDataSet) o;
        return street.equals(that.street);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
