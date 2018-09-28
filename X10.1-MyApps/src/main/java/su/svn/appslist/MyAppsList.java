package su.svn.appslist;

import java.util.Arrays;
import java.util.List;

public class MyAppsList {
    private List<String> items = Arrays.asList(new String[] {"foo", "bar"});

    public MyAppsList() { /* None */ }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
