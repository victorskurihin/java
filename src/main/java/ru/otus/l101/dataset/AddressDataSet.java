package ru.otus.l101.dataset;

import javax.persistence.*;

@Entity
public class AddressDataSet extends DataSet {
    @Column(name = "street")
    private String street;

    //Important for Hibernate
    public AddressDataSet() {
        super(-1);
    }

    public AddressDataSet(long id) {
        super(id);
    }

    public AddressDataSet(long id, String street) {
        super(id);
        this.street = street;
    }

    public String getStreet () {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
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
