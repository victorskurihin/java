package ru.otus.l101.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address_data_set")
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

    public AddressDataSet(String street) {
        super(street.hashCode());
        this.street = street;
    }

    public String getStreet () {
        return street;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
            "street number='" + street + '\'' +
            '}';
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
