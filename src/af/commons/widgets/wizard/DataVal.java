package af.commons.widgets.wizard;

public class DataVal<E> {
    final String page;
    final String name;
    final E val;
    final String group;

    public DataVal(String key, E val) {
        String[] ks = key.split("###");
        page = ks[0];
        name = ks[1];
        group =  ks.length == 3 ? ks[2] : null;
        this.val = val;
    }

    public DataVal(String page, String name, E val) {
        this.page = page;
        this.name = name;
        this.val = val;
        group = null;
    }

    public DataVal(String page, String name, String category, E val) {
        this.page = page;
        this.name = name;
        this.val = val;
        this.group = category;
    }


    public String getPage() {
        return page;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public E getVal() {
        return val;
    }
}
