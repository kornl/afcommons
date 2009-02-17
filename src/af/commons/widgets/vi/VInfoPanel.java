package af.commons.widgets.vi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


/**
 * JPanel which shows the differences between different versions.
 */
public class VInfoPanel extends JPanel {



    JTextPane jtAbout = new JTextPane();
    VReader vreader;
    String since;
    Comparator<String> comparator;
    
    private DefaultStyledDocument getDocument() {
    	DefaultStyledDocument doc = new DefaultStyledDocument();    
		try {
			List<VInfo> infos = vreader.getInfosSince(since);
			for (VInfo s : vreader.getInfosSince(since)) {				 
				doc.insertString(doc.getLength(),
						"Since V"+s.version+": "+s.info+"\n\n", 
						getT());				
			}
			if (infos.size()==0) {
				doc.insertString(doc.getLength(),
						"There are no important changes in the program since the last time it was started.", 
						getT());
			}
		} catch (BadLocationException ble) {
			throw new RuntimeException("Should not happen! BadLocationException in VInfoPanel.getDocument(): "+ble.getMessage());
		}
		return doc;    	
    }
    
	/**
     * Constructor
     * @param inputStreamList inputStream of xml-file.
     * @param comparator Comparator that can compare to version strings.
     * @param since version string since when (exclusive) changes should be shown.
     */
	public VInfoPanel(List<InputStream> inputStreamList, Comparator<String> comparator, String since) throws ParserConfigurationException, SAXException, IOException {
		this.comparator = comparator;
		this.since = since;
		vreader = new VReader(inputStreamList, comparator);
        jtAbout.setStyledDocument(getDocument());
        jtAbout.setEditable(false);
        JScrollPane js = new JScrollPane(jtAbout);
        js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(js);        
	}
	
	/**
     * Constructor
     * @param inputStreamList inputStream of xml-file.
     * @param comparator Comparator that can compare to version strings.
     * @param since version string since when (exclusive) changes should be shown.
     * @param border should a titled border be created for this panel? 
     */
	public VInfoPanel(List<InputStream> inputStreamList, Comparator<String> comparator, String since, boolean border) throws ParserConfigurationException, SAXException, IOException {
		this(inputStreamList, comparator, since);	
		if (border && vreader.getInfosSince(since).size()!=0) {
			setBorder(BorderFactory.createTitledBorder("Changes between versions V"+since));		
		}	
	}
	
    /**
     * Return the SimpleAttributeSet for normal text.
     * @return The SimpleAttributeSet for normal text.
     */
    public static SimpleAttributeSet getT() {
    	SimpleAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attr, "SansSerif");
        StyleConstants.setFontSize(attr, 12);
        return attr;
    }

}
