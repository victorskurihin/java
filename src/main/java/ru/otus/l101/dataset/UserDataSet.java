package ru.otus.l101.dataset;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;
    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    private AddressDataSet address;
    @OneToMany(mappedBy = "userDataSet", cascade = CascadeType.ALL)
    private Set<PhoneDataSet> phones = new HashSet<>();

    //Important for Hibernate
    public UserDataSet() {
        super(-1);
    }

    public UserDataSet(long id) {
        super(id);
    }

    public UserDataSet(long id, String name, AddressDataSet address) {
        this(id);
        this.name = name;
        this.address = address;
    }

    public UserDataSet(String name, AddressDataSet address) {
        this(-1, name, address);
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
    public void addPhone(PhoneDataSet phone) {
        phone.setUserDataSet(this);
        phones.add(phone);
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
