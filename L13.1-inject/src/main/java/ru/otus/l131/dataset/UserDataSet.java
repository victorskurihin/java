package ru.otus.l131.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The class contains a users data. This class is marked up by annotation for
 * working with Hibernate ORM.
 */
@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {
    // for store user name
    @Column(name = "name", unique = true)
    private String name;

    // for store an user's address
    @OneToOne(
        targetEntity = AddressDataSet.class,
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name="address_id")
    private AddressDataSet address;

    // for store a list of user's phones
    @OneToMany(
        mappedBy = "userDataSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER
    )
    private Set<PhoneDataSet> phones = new HashSet<>();

    //Important for Hibernate
    public UserDataSet() {
        super(-1);
    }
    public UserDataSet(long id, String name, AddressDataSet address) {
        super(id);
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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
