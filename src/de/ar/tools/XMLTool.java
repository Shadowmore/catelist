package de.ar.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ListIterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 * Tool for XML operations
 * 
 * @author Shadowmore
 *
 */
public class XMLTool {

	private static SAXBuilder saxBuilder = new SAXBuilder();

	/**
	 * 
	 * Creates an empty Document with a root-element named "ROOT"
	 * 
	 * @return Document
	 */
	public static Document createEmptyXMLDoc() {
		return createEmptyXMLDoc("ROOT");
	}

	/**
	 * 
	 * Creates an empty Document with a root-element named by the parameter rootName
	 * 
	 * @param rootName
	 *            Root-element name
	 * @return Document
	 */
	public static Document createEmptyXMLDoc(String rootName) {
		Document xmlDoc = new Document();
		Element rootNode = new Element(rootName);
		xmlDoc.setRootElement(rootNode);
		return xmlDoc;
	}

	/**
	 * 
	 * Adds a new child-element to the parent-element(<b>parentNode</b>) with the given name(<b>nodeName</b>)
	 * 
	 * @param parentNode
	 *            Parent-element
	 * @param nodeName
	 *            Child-element name
	 * @return new Child-element
	 */
	public static Element addChildNode(Element parentNode, String nodeName) {
		Element childNode = new Element(nodeName);
		parentNode.addContent(childNode);
		return childNode;
	}

	/**
	 * 
	 * Adds a new child-element to the parent-element(<b>parentNode</b>) with the given name(<b>nodeName</b>) and the given text(<b>nodeText</b>)
	 * 
	 * @param parentNode
	 *            Parent-element
	 * @param nodeName
	 *            Child-element name
	 * @param nodeText
	 *            Child-element text
	 * @return new Child-element
	 */
	public static Element addChildNode(Element parentNode, String nodeName, String nodeText) {
		Element childNode = addChildNode(parentNode, nodeName);
		childNode.addContent(nodeText);
		return childNode;
	}

	/**
	 * 
	 * Save the document(<b>xmlDoc</b>) the the given path(<b>fullFilename</b>)
	 * 
	 * @param xmlDoc
	 *            Document to save to
	 * @param fullFilename
	 *            Path and file name to save the document to
	 */
	public static void saveXMLDocumentToFile(Document xmlDoc, String fullFilename) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(fullFilename));
			XMLOutputter xmlOutputter = new XMLOutputter();

			Format myFormat;
			myFormat = xmlOutputter.getFormat();
			myFormat.setOmitEncoding(true);
			myFormat.setOmitDeclaration(true);
			xmlOutputter.setFormat(myFormat);

			xmlOutputter.output(xmlDoc, fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Save the document(<b>xmlDoc</b>) the the given path(<b>fullFilename</b>) with a nice style
	 * 
	 * @param xmlDoc
	 *            Document to save to
	 * @param fullFilename
	 *            Path and file name to save the document to
	 */
	public static void saveXMLDocumentToFileWithIndent(Document xmlDoc, String FullFilename) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(FullFilename));
			XMLOutputter xmlOutputter = new XMLOutputter();

			Format myFormat = Format.getPrettyFormat();
			xmlOutputter.setFormat(myFormat);

			xmlOutputter.output(xmlDoc, fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Loads a xml document from the given url(<b>xmlURL</b>)
	 * 
	 * @param xmlURL
	 *            Url to load from
	 * @return Document
	 */
	public static Document loadXMLFile(URL xmlURL) {
		return loadXMLFile(xmlURL.getPath());
	}

	/**
	 * 
	 * Loads a xml document from the given file(<b>xmlFile</b>)
	 * 
	 * @param xmlFile
	 *            File to load from
	 * @return Document
	 */
	public static Document loadXMLFile(File xmlFile) {
		return loadXMLFile(xmlFile.getAbsolutePath());
	}

	/**
	 * 
	 * Loads a xml document from the given filename(<b>xmlFilename</b>)
	 * 
	 * @param xmlFilename
	 *            Filename to load from
	 * @return Document
	 */
	public static Document loadXMLFile(String xmlFilename) {
		Document xmlDoc = null;

		try {
			xmlDoc = saxBuilder.build(xmlFilename);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return xmlDoc;
	}

	/**
	 * 
	 * Converts the given String(<b>xmlDocString</b>) into a Document
	 * 
	 * @param xmlDocString
	 *            String to convert to
	 * @return Document
	 */
	public synchronized static Document convertStringtoXML(String xmlDocString) {
		Document xmlDoc = null;
		StringReader MyStringReader = new StringReader(xmlDocString);

		try {
			xmlDoc = saxBuilder.build(MyStringReader);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return xmlDoc;
	}

	/**
	 * 
	 * Checks if the given string(<b>xmlDocString</b>) is xml compatible
	 * 
	 * @param xmlDocString
	 *            String to check
	 * @return
	 * @throws Exception
	 */
	public static boolean isXmlStringValid(String xmlDocString) throws Exception {
		Document xmlDoc = null;
		SAXBuilder saxBuilder = new SAXBuilder();
		StringReader MyStringReader = new StringReader(xmlDocString);
		xmlDoc = saxBuilder.build(MyStringReader);
		return (xmlDoc != null); // => true or Exception;
	}

	/**
	 *
	 * Converts the given Element(<b>node</b>) into a String
	 * 
	 * @param node
	 *            Element to convert to
	 * @param pretty
	 *            Pretty or compact format
	 * @return XML as String
	 */
	public static String getXmlAsStr(Element node, boolean pretty) {
		String resultXmlString;
		XMLOutputter xmlOutputter = new XMLOutputter();

		if (pretty) {
			xmlOutputter.setFormat(Format.getPrettyFormat());
		} else {
			xmlOutputter.setFormat(Format.getCompactFormat());
		}

		try {
			resultXmlString = xmlOutputter.outputString(node);
		} catch (RuntimeException e) {
			resultXmlString = null;
		}
		return resultXmlString;
	}

	/**
	 * 
	 * Merges two Documents into one
	 * 
	 * @param xmlDocMergeDest
	 *            XML to merge into
	 * @param xmlDocMergeSource
	 *            XML to merge from
	 */
	@SuppressWarnings("rawtypes")
	public static void mergeXmlDoc(Document xmlDocMergeDest, Document xmlDocMergeSource) {
		for (ListIterator it = xmlDocMergeSource.getRootElement().getChildren().listIterator(); it.hasNext();) {
			Element node = (Element) it.next();
			xmlDocMergeDest.getRootElement().addContent(node.clone());
		}
	}
}