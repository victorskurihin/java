package ru.otus.l101.dataset;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;
    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    private AddressDataSet address;
    @OneToMany(targetEntity = PhoneDataSet.class, mappedBy = "userDataSet", fetch=FetchType.EAGER)
    @Fetch(value= FetchMode.SELECT)
    private Set<PhoneDataSet> phones = new HashSet<>();

    //Important for Hibernate
    public UserDataSet() {
        super(-1);
    }

    public UserDataSet(long id) {
        super(id);
    }

    public UserDataSet(long id, String name, AddressDataSet address, Set<PhoneDataSet> phones) {
        this(id);
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public UserDataSet(String name, AddressDataSet address, Set<PhoneDataSet> phones) {
        this(-1, name, address, phones);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddress() {
        return address;
    }
    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }
    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
               " id'" + getId() + "'" +
               ", name='"   + name + "'" +
               ", address=" + address +
               ", phones="  + phones +
               " }";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + 13 * address.hashCode() + 31 * phones.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataSet that = (UserDataSet) o;
        return  name.equals(that.name) &&
                address.equals(that.address) &&
                phones.equals(that.phones);
    }
}
