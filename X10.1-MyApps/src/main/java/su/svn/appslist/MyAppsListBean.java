package su.svn.appslist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAppsListBean {
    private List<App> items = new ArrayList<>();

    public MyAppsListBean() {
        items.add(new App(0, "myhello", "/myhello"));
        items.add(new App(1, "myreqinfo", "/myreqinfo"));
        items.add(new App(2, "myrequestheader", "/myrequestheader"));
        items.add(new App(3, "mytestpostgre", "/mytestpostgre"));
    }

    public List<App> getItems() {
        return items;
    }

    public void setItems(List<App> items) {
        this.items = items;
    }
}
