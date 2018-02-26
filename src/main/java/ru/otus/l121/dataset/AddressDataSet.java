package ru.otus.l121.dataset;

import javax.persistence.*;

/**
 * TODO
 */
@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {
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