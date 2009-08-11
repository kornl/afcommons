package org.af.commons.widgets.buttons;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 *
 */
public class HorizontalButtonPane extends JPanel {
    private static final long serialVersionUID = 1L;
    private final String[] cmds;
    private final JButton[] buttons;

    public static final String OK_CMD = "OK";
    public static final String CANCEL_CMD = "CANCEL";
    public static final String APPLY_CMD = "APPLY";


    public HorizontalButtonPane(List<String> captions, List<String> cmds) {
        this(captions.toArray(new String[1]), cmds.toArray(new String[1]));
    }

    public HorizontalButtonPane(String[] captions, String[] cmds) {
        this.cmds = cmds;
        buttons = new JButton[cmds.length];

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(captions[i]);
            buttons[i].setActionCommand(cmds[i]);
            add(buttons[i]);
            add(Box.createHorizontalStrut(5));
        }
    }

    public void addActionListener(ActionListener al) {
        for (JButton b : buttons) {
            b.addActionListener(al);
        }
    }


    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (JButton b : buttons) {
            b.setEnabled(enabled);
        }
    }

    public void setEnabled(int i, boolean enabled) {
        buttons[i].setEnabled(enabled);
    }

}
