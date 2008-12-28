package af.commons.widgets.lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class MyJComboBox<E> extends JComboBox {
	
	public MyJComboBox(E[] objects, String[] names) {
       super(new MyJComboBoxModel<E>(Arrays.asList(objects), Arrays.asList(names)));
    }

    public MyJComboBox() {
        super(new MyJComboBoxModel<E>());
    }

    public MyJComboBox(E[] objects) {
        this(Arrays.asList(objects));
    }

    public MyJComboBox(List<E> objects) {
        super(new MyJComboBoxModel<E>());
        setModel(objects);
    }

    public MyJComboBox(List<E> objects, List<String> names) {
        super(new MyJComboBoxModel<E>(objects, names));
    }

    public MyJComboBoxModel<E> getModel() {
        return (MyJComboBoxModel<E>) (super.getModel());
    }

    public String getSelectedItem() {
        return getSelectedName();
    }

    public String getSelectedName() {
        return getModel().getNameAt(getSelectedIndex());
    }

    public E getSelectedObject() {
        return getModel().getObjectAt(getSelectedIndex());
    }


    public void setModel(ComboBoxModel aModel) {
        if (aModel instanceof MyJComboBoxModel)
            setModel((MyJComboBoxModel)aModel);
        else
            throw new RuntimeException("Wrong ComboBoxModel!");
    }

    public void setModel(MyJComboBoxModel model) {
        super.setModel(model);
    }

    public void setModel(List<E> objects) {
        List<String> names = new ArrayList<String>();
        for (E o:objects)
            names.add(o.toString());
        setModel(new MyJComboBoxModel<E>(objects, names));
    }

    public void setModel(List<E> objects, List<String> names) {
        setModel(new MyJComboBoxModel<E>(objects, names));
    }

    public void setModel(E[] objects, String[] names) {
        setModel(new MyJComboBoxModel<E>(Arrays.asList(objects), Arrays.asList(names)));
    }

//    public void setSelectedItem(Object name) {
//        setSelectedIndex(getModel().getIndexOf(name));
//    }

    public void setSelectedObject(E o) {
        setSelectedIndex(getModel().getIndexOfObject(o));
    }

    public void setSelectedName(String name) {
        setSelectedItem(name);
    }
}
