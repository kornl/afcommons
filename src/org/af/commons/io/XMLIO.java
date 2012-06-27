package org.af.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLIO {
	
	/**
	 * Reads a XML file.
	 * @param file xml file that should be read in.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Document readXML(File file) throws ParserConfigurationException, SAXException, IOException {
		Document document = null;
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		document = builder.parse(file);
	    return document;
	}
	
	/**
	 * Speichert eine XML-Datei.
	 * @param document the xml document to be saved.
	 * @param filename the file the xml document should be saved to.
	 * @throws TransformerFactoryConfigurationError 
	 * @throws FileNotFoundException 
	 * @throws TransformerException 
	 */
	public static void saveXML(Document document, File file) throws TransformerFactoryConfigurationError, FileNotFoundException, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new PrintStream(file));
		transformer.transform(source, result);
	}
}
