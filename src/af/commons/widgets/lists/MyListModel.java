package af.commons.widgets.lists;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;


public class MyListModel<T> extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	private List<T> data;


    public MyListModel() {                 
        data = new ArrayList<T>();
        fireContentsChanged();
    }

    public MyListModel(List<T> data) {
        this();
        addElements(data);

    }


    public int getSize() {
        return data.size();
    }

    public T getElementAt(int index) {
        return data.get(index);
    }

    public void addElement(T element){
        data.add(element);
        fireContentsChanged();
    }

    public void addElements(List<T> elements){
        data.addAll(elements);
        fireContentsChanged();
    }

    void removeElements(List<T> elements){
        data.removeAll(elements);
        fireContentsChanged();
    }

    void retainElements(int[] indices){
        List<T> result = new ArrayList<T>();
        for (int i:indices)
            result.add(data.get(i));
        data = result;
        fireContentsChanged();
    }

    void removeElements(int[] indices){
        List<Integer> retain = new ArrayList<Integer>();
        for (int i=0; i<getSize(); i++)
            retain.add(i);
        for (int i:indices)
            retain.remove(new Integer(i));
        int[] retain2 = new int[retain.size()];
        for (int i=0; i<retain.size(); i++)
            retain2[i] = retain.get(i);
        retainElements(retain2);
    }

    public List<T> getAllElements() {
        List<T> result = new ArrayList<T>();
        result.addAll(data);
        return(result);
    }

    private void fireContentsChanged() {
        fireContentsChanged(this, 0, getSize()-1);
    }
}
