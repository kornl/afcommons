package org.af.commons.logging.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.af.commons.logging.ApplicationLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * LogDialog shows the logging messages.
 */
public class SimpleLogPanel extends JPanel implements ActionListener, Observer {
    private static Log logger = LogFactory.getLog(SimpleLogPanel.class);

    private static final long serialVersionUID = 1L;

    private ApplicationLog appLog;

    private JTextPane jtfLog;

    private JButton jbempty;

    private Level[] verbosity = {Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.ALL};
    private JRadioButton[] vbuttons = new JRadioButton[verbosity.length];
    private ButtonGroup buttonGroup = new ButtonGroup();
    private Hashtable<String, Level> buttonCmd2Level;
//    private JCheckBox cbRCmds, cbRConsole;

    //private Level level = Level.ALL;

    private AbstractDocument doc = null;

//    public LogPanel() {
//        this(true);
//    }

    /**
     * Standard constructor
     *
     */
    public SimpleLogPanel(ApplicationLog appLog) {
//        super("Log");

        this.appLog = appLog;
        this.appLog.addObserver(this);

        makeComponents();
        doTheLayout();
    }


    private void makeComponents() {

        jtfLog = new JTextPane();
        jtfLog.setEditable(false);
        jtfLog.setPreferredSize(new Dimension(600, 300));
        StyledDocument styledDoc = jtfLog.getStyledDocument();
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument) styledDoc;
        } else {
            logger.debug("No AbstractDocument.");
        }

        jbempty = new JButton("Clear");
        jbempty.addActionListener(this);

        buttonCmd2Level = new Hashtable<String, Level>();
        for (int i = 0; i < verbosity.length; i++) {
            vbuttons[i] = new JRadioButton(verbosity[i].toString());
            vbuttons[i].setActionCommand(verbosity[i].toString());
            buttonGroup.add(vbuttons[i]);
            vbuttons[i].addActionListener(this);
            buttonCmd2Level.put(vbuttons[i].getActionCommand(), verbosity[i]);
        }

        vbuttons[3].setSelected(true);
//        cbRCmds = new JCheckBox("R Cmds");
//        cbRConsole = new JCheckBox("R Console");
//        cbRCmds.addActionListener(this);
//        cbRConsole.addActionListener(this);
//        cbRCmds.setSelected(true);
//        cbRConsole.setSelected(true);

        fillTextField();
    }

    private void doTheLayout() {

//        JPanel cp = new JPanel();
        String cols = "25dlu:grow, 20dlu, pref, 20dlu, right:pref:grow";
        String rows = "fill:100dlu:grow, 5dlu, pref, 5dlu, pref, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        setLayout(layout);
        CellConstraints cc = new CellConstraints();

        add(new JScrollPane(jtfLog),               cc.xyw(1, 1, 5));
        add(createButtonPanel(),                   cc.xyw(3, 5, 1));
        add(jbempty,                               cc.xy (5, 5));
    }

    private JPanel createButtonPanel() {
        JPanel cp = new JPanel();
        CellConstraints cc = new CellConstraints();

        String rows = "pref";
        String cols = "pref,pref";

        for (int i = 0; i < vbuttons.length; i++) {
           cols += ",pref";
        }

        FormLayout layout = new FormLayout(cols, rows);
        cp.setLayout(layout);

        for (int i = 0; i < vbuttons.length; i++) {
            cp.add(vbuttons[i], cc.xy(i+1, 1));
        }
//        cp.add(cbRCmds, cc.xy(vbuttons.length+1, 1));
//        cp.add(cbRConsole, cc.xy(vbuttons.length+2, 1));
        return cp;
    }


    /**
     * Writes the Messages of the LoggingEvents with an Level greater or equal to the selected one
     * to the TextField.
     */
    public void fillTextField() {
        jtfLog.setText("");
        String cmd = buttonGroup.getSelection().getActionCommand();
        Level verb = buttonCmd2Level.get(cmd);
        List<LoggingEvent> les = appLog.getEventsAbove(verb);
        for (LoggingEvent le : les) {
            append(le);
        }
    }

    /**
     * Evaluates ActionEvents.
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (jbempty.getText().equals(e.getActionCommand())) {
            appLog.reset();
//            jtfLog.setText("");
//			levents = new Vector<LoggingEvent>();
        } else {
            fillTextField();
        }
    }

    public void append(List<LoggingEvent> les) {
        for (LoggingEvent le : les) {
            append(le);
        }
    }


    /**
     * Appends messages of LoggingEvents to the TextField.
     *
     * @param le The LoggingEvent to append.
     */
    public void append(LoggingEvent le) {
        if (doc == null) {
            return;
        }
        try {
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attr, "SansSerif");
            String str = le.getMessage().toString();
            ThrowableInformation ti = le.getThrowableInformation();
            /* Font Size */
            if (le.getLevel().isGreaterOrEqual(Level.INFO)) {
                StyleConstants.setFontSize(attr, 12);
            } else {
                StyleConstants.setFontSize(attr, 10);
            }
            /* Font Color */
            if (le.getLevel().isGreaterOrEqual(Level.ERROR)) {
                StyleConstants.setForeground(attr, Color.RED);
            } else if (le.getLevel().isGreaterOrEqual(Level.WARN)) {
                StyleConstants.setForeground(attr, Color.ORANGE);
            } else if (new StringBuffer(le.getMessage().toString()).indexOf("R cmd:") == 0) {
                str = str.substring(7);
                StyleConstants.setForeground(attr, Color.BLUE);
            } else if (new StringBuffer(le.getMessage().toString()).indexOf("R Console:") == 0) {
                StyleConstants.setForeground(attr, new Color(0, 150, 0));
            } else {
                StyleConstants.setForeground(attr, Color.BLACK);
            }
            doc.insertString(doc.getLength(),
                    str + "\n",
                    attr);
            if (ti != null) {
                for (String s : ti.getThrowableStrRep()) {
                    doc.insertString(doc.getLength(), s + "\n", attr);
                }
            }
        } catch (BadLocationException ble) {

        }
    }

    public void update(Observable o, Object arg) {
        if (arg == null)
            fillTextField();
        else {
            appLog = (ApplicationLog) o;
            LoggingEvent e = (LoggingEvent) arg;
            append(e);
        }
    }
}
