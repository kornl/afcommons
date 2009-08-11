package org.af.commons.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTMLPaneWithButtons extends HTMLPane implements HyperlinkListener {

	private static Log logger = LogFactory.getLog(HTMLPaneWithButtons.class);
    protected Hashtable<String, ActionListener> cmdToListener = new Hashtable<String, ActionListener>();

    public HTMLPaneWithButtons() {
        addHyperlinkListener(this);        
    }

    public String makeButtonString(String label, ActionListener al) {
        String cmd = "" + cmdToListener.size();
        cmdToListener.put(cmd, al);
        return makeButtonString(label, cmd);
    }

    public String makeButtonString(String label, String cmd, ActionListener al) {
        if (cmdToListener.get(cmd)!=null) {logger.warn("ActionListener for command "+cmd+" will be overwritten.");}
        cmdToListener.put(cmd, al);
        return makeButtonString(label, cmd);
    }

    public String makeButtonString(String label, String cmd) {
        String s = "<a href=\"" + cmd + "\">" + label + "</a>";
        return s;
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                HTMLDocument doc = (HTMLDocument) pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                System.out.println(e.getDescription());
                fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, e.getDescription()));
            }
        }
    }

    protected void fireActionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();
        cmdToListener.get(cmd).actionPerformed(event);
    }
}
