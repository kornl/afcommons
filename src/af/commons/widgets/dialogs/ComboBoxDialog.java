package af.commons.widgets.dialogs;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import af.commons.widgets.WidgetFactory;
import af.commons.widgets.buttons.OkCancelButtonPane;
import af.commons.widgets.lists.MyJComboBox;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ComboBoxDialog<E> extends JDialog implements ActionListener {
    private MyJComboBox<E> cb;
    private OkCancelButtonPane bp;
    private E result = null;

    public ComboBoxDialog(Component parent, String title, String msg, List<E> values, List<String> labels, E initVal) {
        super((Window)null, title);
        setModal(true);
        setLocationRelativeTo(parent);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                result = null;
            }
        });
        cb = new MyJComboBox<E>(values, labels);
        cb.setSelectedObject(initVal);
        JPanel p = new JPanel();
        bp = new OkCancelButtonPane();
        bp.addActionListener(this);
        FormLayout layout = new FormLayout("fill:pref:grow", "pref, 5dlu, fill:pref:grow");
        setContentPane(p);
        p.setLayout(layout);
        CellConstraints cc = new CellConstraints();
        p.add(new JLabel(msg),    cc.xy(1,1));
        p.add(cb,                 cc.xy(1,3));
        p = WidgetFactory.makeDialogPanelWithButtons(p, this);
        setContentPane(p);
    }

    public ComboBoxDialog(Component parent, String title, String msg, List<E> values, List<String> labels) {
        this(parent, title, msg,  values, labels, values.get(0));
    }

    public ComboBoxDialog(Component parent, String title, String msg, E[] values, String[] labels, E initVal) {
        this(parent, title, msg,  Arrays.asList(values), Arrays.asList(labels), initVal);
    }

    public ComboBoxDialog(Component parent, String title, String msg, E[] values, String[] labels) {
        this(parent, title, msg,  Arrays.asList(values), Arrays.asList(labels));
    }


    public E showAndWaitForInput() {
        pack();
        setVisible(true);
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(OkCancelButtonPane.OK_CMD)) {
            result = cb.getSelectedObject();
        } else if (e.getActionCommand().equals(OkCancelButtonPane.CANCEL_CMD)) {
            result = null;
        }
        dispose();
    }
    
}
