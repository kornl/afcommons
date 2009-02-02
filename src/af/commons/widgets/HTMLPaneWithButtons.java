package af.commons.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringWriter;
import java.util.Hashtable;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTMLPaneWithButtons extends JTextPane implements HyperlinkListener {


	private static Log logger = LogFactory.getLog(HTMLPaneWithButtons.class);
    private HTMLEditorKit kit;
    private HTMLDocument doc;
    //    private List<ActionListener> listeners = new ArrayList<ActionListener>();
    private Element body;
    protected Hashtable<String, ActionListener> cmdToListener = new Hashtable<String, ActionListener>();

    public HTMLPaneWithButtons() {
        setContentType("text/html");
        addHyperlinkListener(this);
        setEditable(false);
        kit = (HTMLEditorKit) getEditorKit();
        doc = (HTMLDocument) getDocument();
        Element html = doc.getRootElements()[0];
        body = html.getElement(0);
    }

//    public void appendLine(String s) {
//        appendHTML(s + "<br>");
//    }

    public void appendParagraph(String s) {
        appendHTML("<P align=\"left\">" + s + "</P>");
    }

    public void appendHeadline(String s) {
        appendHTML("<h1>" + s + "</h1>");
    }

//    public void appendButton(String label, ActionListener al) {
//        String cmd = ""+cmdToListener.size();
//        cmdToListener.put(cmd, al);
//        appendHTML(makeButtonString(label, cmd));
//    }

    public void appendHTML(String s) {
        try {
            doc.insertBeforeEnd(body, s);
            setCaretPosition(doc.getLength());
            logger.debug("HTML: "+s);
        } catch (Exception e) {
            //TODO we dont pass this along, bad style, etc, STFU
            e.printStackTrace();
        }
    }

    public void print2() {
        try {
            StringWriter sw = new StringWriter();
            kit.write(sw, doc, 0, doc.getLength());
            System.out.println(sw.getBuffer().toString());
        } catch (Exception e) {
            //TODO we dont pass this along, bad style, etc, STFU
            e.printStackTrace();
        }
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

    public String makeBold(String s) {
        return "<B>" + s + "</B>";
    }

    public String makeBoldItalics(String s) {
        return "<B><i>" + s + "</i></B>";
    }

    public String makeBoldUnderlined(String s) {
        return "<B><u>" + s + "</u></B>";
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

//    public void addActionListener(ActionListener listener) {
//        listeners.add(listener);
//
//    }
}
