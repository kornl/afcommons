package af.commons.widgets.lists;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

public class MyJComboBoxModel<E> extends DefaultComboBoxModel {
    // implements javax.swing.ListModel
    private List<E> objects;
    //private List<String> names;

    public MyJComboBoxModel() {
        this(new ArrayList<E>(), new ArrayList<String>());
    }

    public MyJComboBoxModel(List<E> objects, List<String> names) {
        this.objects = objects;
        //this.names = names;
        for (String s:names)
            addElement(s);
    }


    public E getObjectAt(int i) {
        return objects.get(i);
    }

    public String getNameAt(int i) {
        return getElementAt(i);
    }

    public String getElementAt(int i) {
        Object o = super.getElementAt(i);
        return  o == null ? null : o.toString();
    }

    public int getIndexOf(Object name) {
        return super.getIndexOf(name.toString());
    }

    public int getIndexOfName(String name) {
        return getIndexOf(name);
    }

    public int getIndexOfObject(Object o) {
        return objects.indexOf(o);
    }

    public void addElement(String name, E obj) {
        addElement(name);
        objects.add(obj);
    }
}
