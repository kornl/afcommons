package af.commons.widgets.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {
    private Map data;

    public DataManager(Map data) {
        this.data = data;
    }

    public List<DataVal> getVals(String page, String name, String group) {
        java.util.List<DataVal> result = new ArrayList<DataVal>();
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

    public List<DataVal> getValsInPage(String page, String group) {
        return getVals(page, null, group);
    }

    public List<DataVal> getValsInPage(String page) {
        return getVals(page, null, null);
    }

    public DataVal getVal(String name) {
        return getVals(null, name, null).get(0);
    }


}
