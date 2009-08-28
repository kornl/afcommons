package org.af.commons.widgets.buttons;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * Horizontal pane with buttons.
 */
public class HorizontalButtonPane extends JPanel {
    protected final String[] cmds;
    protected final JButton[] buttons;

    public static final String OK_CMD = "OK";
    public static final String CANCEL_CMD = "CANCEL";
    public static final String APPLY_CMD = "APPLY";

    /**
     * Constructor
     *
     * @param captions List of captions for buttons.
     * @param cmds List of action command names for buttons.
     */
    public HorizontalButtonPane(List<String> captions, List<String> cmds) {
        this(captions.toArray(new String[1]), cmds.toArray(new String[1]));
    }


    /**
     * Constructor
     *
     * @param captions Array of captions for buttons.
     * @param cmds Array of action command names for buttons.
     */
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

    /**
     * Add an action listener to all buttons.
     *
     * @param al The ActionListener.
     */
    public void addActionListener(ActionListener al) {
        for (JButton b : buttons) {
            b.addActionListener(al);
        }
    }

    /**
     * Enable/Disable all buttons.
     *
     * @param enabled True if buttons should be enabled.
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (JButton b : buttons) {
            b.setEnabled(enabled);
        }
    }

    /**
     * Enable/disable the ith button.
     *
     * @param i       Index of button.
     * @param enabled True if button should be enabled.
     */
    public void setEnabled(int i, boolean enabled) {
        buttons[i].setEnabled(enabled);
    }

    /**
     * Set the caption of the ith button.
     *
     * @param i    Index of button.
     * @param text Text for button.
     */
    public void setText(int i, String text) {
        buttons[i].setText(text);
    }
}
