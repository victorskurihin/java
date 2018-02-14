package ru.otus.l101.dataset;

import javax.persistence.*;

@Entity
@Table(name = "user_data_set")
public class UserDataSet extends DataSet {
    @Transient public final int PRIME = 131071;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private PhoneDataSet phone;

    //Important for Hibernate
    public UserDataSet() {
        super(-1);
    }

    public UserDataSet(long id) {
        super(id);
    }

    public UserDataSet(long id, String name, PhoneDataSet phone) {
        this(id);
        this.name = name;
        this.phone = phone;
    }

    public UserDataSet(String name, PhoneDataSet phone) {
        this(-1, name, phone);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
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
            "name='" + name + '\'' +
            ", phone=" + phone +
            '}';
    }

    @Override
    public int hashCode() {
        return phone.hashCode() + (int) (
            PRIME * super.getId() % Integer.MAX_VALUE
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataSet that = (UserDataSet) o;
        return  super.getId() == that.getId() &&
                name.equals(that.name) &&
                phone.equals(that.phone);
    }
}
