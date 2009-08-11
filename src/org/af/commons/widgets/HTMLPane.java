package org.af.commons.widgets;

import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTMLPane extends JTextPane {

	protected static Log logger = LogFactory.getLog(HTMLPaneWithButtons.class);
	protected HTMLEditorKit kit;
    protected HTMLDocument doc;
    protected Element body;

    public HTMLPane() {
        setContentType("text/html");
        setEditable(false);
        kit = (HTMLEditorKit) getEditorKit();
        doc = (HTMLDocument) getDocument();
        Element html = doc.getRootElements()[0];
        body = html.getElement(0);
    }

    public void appendParagraph(String s) throws BadLocationException, IOException {
        appendHTML("<P align=\"left\">" + s + "</P>");
    }

    public void appendHeadline(String s) throws BadLocationException, IOException {
        appendHTML("<h1>" + s + "</h1>");
    }

    public void appendHTML(String s) throws BadLocationException, IOException {        
    	doc.insertBeforeEnd(body, s);
    	setCaretPosition(doc.getLength());
    }

    public String makeBold(String s) {
        return "<b>" + s + "</B>";
    }

    public String makeBoldItalics(String s) {
        return "<b><i>" + s + "</i></b>";
    }

    public String makeBoldUnderlined(String s) {
        return "<b><u>" + s + "</u></b>";
    }

    public void clear() {
        // dont really know what i am doing here but seems to work
        setDocument(new HTMLDocument());
        setEditorKit(kit);
        doc = (HTMLDocument) getDocument();
        Element html = doc.getRootElements()[0];
        body = html.getElement(0);
    }

}

