package ru.otus.l101.dataset;

import javax.persistence.*;

@Entity
@Table(name = "user_data_set")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToOne(cascade = CascadeType.ALL)
    private PhoneDataSet phone;

    //Important for Hibernate
    public UserDataSet() {
        super(-1);
    }

    public UserDataSet(long id) {
        super(id);
    }

    public UserDataSet(long id, String name, AddressDataSet address, PhoneDataSet phone) {
        this(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public UserDataSet(String name, AddressDataSet address, PhoneDataSet phone) {
        this(-1, name, address, phone);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddress() {
        return address;
    }
    private void setPhone(AddressDataSet address) {
        this.address = address;
    }
    public PhoneDataSet getPhone() {
        return phone;
    }

    private void setPhone(PhoneDataSet phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
            "id'" + getId() + '\'' +
            ", name='"   + name + '\'' +
            ", address=" + address +
            ", phone="   + phone +
            '}';
    }

    @Override
    public int hashCode() {
        return name.hashCode() + 13 * address.hashCode() + 31 * phone.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataSet that = (UserDataSet) o;
        return  name.equals(that.name) &&
                address.equals(that.address) &&
                phone.equals(that.phone);
    }
}
