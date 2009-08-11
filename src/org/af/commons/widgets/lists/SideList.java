package org.af.commons.widgets.lists;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;


public class SideList<T> extends JList {
    //private SplitList splitList;



	public SideList(MyListModel<T> dataModel) {
        super(dataModel);
    }

    public MyListModel<T> getModel() {
        return (MyListModel<T>) super.getModel();
    }

    List<T> getSelecectedValues() {
        List<T> result = new ArrayList<T>();
        int[] indices = getSelectedIndices();
        for (int i:indices) result.add(getModel().getElementAt(i));
        return result;
    }
}
