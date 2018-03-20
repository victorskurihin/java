package ru.otus.l101.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

import java.util.ArrayList;
import java.util.List;

public class TestDataSetOneToManyClass extends DataSet {
    public List<TestDataSetClass> fList;

    public TestDataSetOneToManyClass(long id) {
        super(id);
        fList = new ArrayList<>();
        fList.add(new TestDataSetClass(1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestDataSetOneToManyClass that = (TestDataSetOneToManyClass) o;
        return fList.equals(that.fList);
    }

    @Override
    public int hashCode() {

        return fList.hashCode();
    }

    @Override
    public String toString() {
        return "TestDataSetOneToManyClass{" +
            " fList=" + fList +
            '}';
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
