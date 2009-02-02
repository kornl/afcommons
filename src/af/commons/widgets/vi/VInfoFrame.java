package af.commons.widgets.vi;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import af.commons.widgets.WidgetFactory;
import af.commons.widgets.buttons.OKButtonPane;

/**
 * This JFrame shows the version info
 */
public class VInfoFrame extends JFrame implements ActionListener {


    
	/**
     * Constructor
     * @param inputStream inputStream of xml-file.
     * @param comparator Comparator that can compare to version strings.
     * @param since version string since when (exclusive) changes should be shown.
     */
	public VInfoFrame(List<InputStream> inputStreamList, Comparator<String> comparator, String since) throws ParserConfigurationException, SAXException, IOException {
		super("Version Information");		
		constructMe(inputStreamList, comparator, since);
	}
		    
	public VInfoFrame(InputStream inputStream, Comparator<String> comparator,
			String since) throws ParserConfigurationException, SAXException, IOException {
		Vector<InputStream> inputStreamList = new Vector<InputStream>();
		inputStreamList.add(inputStream);
		constructMe(inputStreamList, comparator, since);
	}

	private void constructMe(List<InputStream> inputStreamList, Comparator<String> comparator,
			String since) throws ParserConfigurationException, SAXException, IOException {
		Container cp = getContentPane();
		cp.add(new VInfoPanel(inputStreamList, comparator, since));
	    cp = WidgetFactory.makeDialogPanelWithButtons(cp, new OKButtonPane(), this);
	    setContentPane(cp);
	    pack();
	    setLocationRelativeTo(null);
	    setVisible(true);		
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(OKButtonPane.OK_CMD)) {
			dispose();
		}
	}

}
