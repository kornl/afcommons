package af.commons.widgets.vi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class VReader {

	HashMap<String,String> vinfo = new HashMap<String,String>();
	Comparator<String> comparator;
	
	public void apply(Node node) {
		if("vinfo".equals(node.getNodeName())) {
			String version = node.getAttributes().getNamedItem("version").getTextContent();
			String info = node.getFirstChild().getTextContent();	
			vinfo.put(version, info);
		}
	}
	
	public List<VInfo> getInfosSince(String version) {
		Vector<VInfo> infos = new Vector<VInfo>();
		Vector<String> keys = new Vector<String>(vinfo.keySet());
		Collections.sort(keys, comparator);
		for (String key : keys) {
			if (comparator.compare(version, key) < 0) {
				infos.add(new VInfo(key, vinfo.get(key)));
			}
		}
		return infos;
	}
	
	/**
	 * Notice that there is only ONE information shown for each version number (the last in the List). 
	 * @param inputStreamList
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void readFiles(List<InputStream> inputStreamList) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	

		for (InputStream inputStream : inputStreamList) {
			Document document = builder.parse(inputStream);

			Element root = (Element) document.getChildNodes().item(0);
			NodeList children = root.getChildNodes();
			for (int i=0; i< children.getLength(); i++) {
				Node node = children.item(i);
				if (node.getNodeType()==Node.ELEMENT_NODE) apply(node);				
			}
		}
	}
	
	public VReader(List<InputStream> inputStreamList, Comparator<String> comparator) throws ParserConfigurationException, SAXException, IOException {
		this.comparator = comparator;
		readFiles(inputStreamList);
	}

}
