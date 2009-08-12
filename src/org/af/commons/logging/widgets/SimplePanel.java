package org.af.commons.logging.widgets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.af.commons.logging.ApplicationLog;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


abstract public class SimplePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    protected JTextArea textArea;
    protected ApplicationLog appLog;

    /**
     * Standard constructor
     *
     */
    public SimplePanel(ApplicationLog appLog) {
        this.appLog = appLog;
        makeComponents();
        doTheLayout();
    }


    abstract protected String getText();

    protected void makeComponents() {
        textArea = new JTextArea(5, 10);
        textArea.setText(getText());
        textArea.setEditable(false);
    }

    private void doTheLayout() {
        String cols = "fill:pref:grow";
        String rows = "fill:pref:grow";
        FormLayout layout = new FormLayout(cols, rows);

        setLayout(layout);
        CellConstraints cc = new CellConstraints();

        add(new JScrollPane(textArea), cc.xy(1, 1));
    }

    protected ApplicationLog getAppLog() {
        return appLog;
    }
}
