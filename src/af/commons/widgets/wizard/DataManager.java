package af.commons.widgets.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {
    private Map data;

    public DataManager(Map data) {
        this.data = data;
    }

    public List<DataVal> getDataVals(String page, String name, String group) {
        List<DataVal> result = new ArrayList<DataVal>();
        for (Object k:data.keySet()) {
            String key = k.toString();
            Object val = data.get(k);
            String[] ks = key.split("###");
            if (    (page == null || ks[0].equals(page)) &&
                    (name == null || ks[1].equals(name)) &&
                    (group == null || (ks.length == 3 && ks[2].equals(group)))) {
                result.add(new DataVal(key, val));
            }
        }
        return result;
    }

    public <E> List<E> getVals(String page, String name, String group, Class<E> c) {
        List<DataVal> dvs = getDataVals(page, name, group);
        List<E> result = new ArrayList<E>();
        for (DataVal dv:dvs) result.add((E)dv.getVal());
        return result;
    }


    public List<DataVal> getDataValsInPage(String page, String group) {
        return getDataVals(page, null, group);
    }

    public <E> List<E> getValsInPage(String page, String group, Class<E> c) {
        return getVals(page, null, group, c);
    }

    public <E> List<E> getValsInPage(String page, Class<E> c) {
        return getVals(page, null, null, c);
    }


    public List<DataVal> getDataValsInPage(String page) {
        return getDataVals(page, null, null);
    }

    public Object getVal(String name) {
        return getDataVal(name).getVal();
    }

    public DataVal getDataVal(String name) {
        return getDataVals(null, name, null).get(0);
    }


}
