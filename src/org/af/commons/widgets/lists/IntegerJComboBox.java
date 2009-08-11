package org.af.commons.widgets.lists;

import java.util.ArrayList;
import java.util.List;

public class IntegerJComboBox extends MyJComboBox<Integer>{
    public IntegerJComboBox(int from, int to) {
        List<Integer> ints = new ArrayList<Integer>();
        List<String> names = new ArrayList<String>();
        for (Integer i=from; i<=to; i++) {
            ints.add(i);
            names.add(i.toString());
        }
        setModel(ints, names);
    }
}
