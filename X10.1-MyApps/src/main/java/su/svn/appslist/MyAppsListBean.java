package su.svn.appslist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAppsListBean {
    private List<App> items = new ArrayList<>();

    public MyAppsListBean() {
        items.add(new App(0, "name0", "location0"));
        items.add(new App(1, "name1", "location1"));
    }

    public List<App> getItems() {
        return items;
    }

    public void setItems(List<App> items) {
        this.items = items;
    }
}
