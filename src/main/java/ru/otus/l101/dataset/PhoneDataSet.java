package ru.otus.l101.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "phone_data_set")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;

    //Important for Hibernate
    public PhoneDataSet() {
        super(-1);
    }

    public PhoneDataSet(long id) {
        super(id);
    }

    public PhoneDataSet(String number) {
        super(number.hashCode());
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
            "number='" + number + '\'' +
            '}';
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
