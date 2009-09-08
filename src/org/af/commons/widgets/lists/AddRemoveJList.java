package org.af.commons.widgets.lists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.af.commons.Localizer;
import org.af.commons.widgets.validate.ValidatedTextField;
import org.af.commons.widgets.validate.ValidationException;


public class AddRemoveJList<E> extends JPanel implements ActionListener {
    private JList list;
    private MyListModel<E> model;
    private ValidatedTextField<E> tf;
    private JButton bAdd = new JButton(
            Localizer.getInstance().getString("AFCOMMONS_WIDGETS_LISTS_ADD")
    );

    public AddRemoveJList(E[] data, ValidatedTextField<E> tf) {
        this(Arrays.asList(data), tf);
    }

    public AddRemoveJList(List<E> data, ValidatedTextField<E> tf) {
        model = new MyListModel<E>(data);
        list = new JList(model);
        // TODO diable selection in list?
        this.tf = tf;

        bAdd.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Box b = Box.createHorizontalBox();
        b.add(tf);
        b.add(Box.createVerticalStrut(10));
        b.add(bAdd);

        add(Box.createVerticalStrut(5));
        add(new JScrollPane(list));
        add(Box.createVerticalStrut(5));
        add(b);
        add(Box.createVerticalStrut(5));
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bAdd) {
            try {
                E obj = tf.getValidatedValue();
                model.addElement(obj);
            } catch (ValidationException exc) {
                JOptionPane.showMessageDialog(this, tf.getValidationErrorMsg());
            }
        }
    }

    public JList getJList() {
        return list;
    }

    public ValidatedTextField<E> getTextField() {
        return tf;
    }

    public List<E> getSelectedValues() {
        return model.getAllElements();
    }
}
