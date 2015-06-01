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

public class XMLTool {

	private static SAXBuilder saxBuilder = new SAXBuilder();

	public static Document createEmptyXMLDoc() {
		return createEmptyXMLDoc("ROOT");
	}

	public static Document createEmptyXMLDoc(String rootName) {
		Document xmlDoc = new Document();
		Element rootNode = new Element(rootName);
		xmlDoc.setRootElement(rootNode);
		return xmlDoc;
	}

	public static Element addChildNode(Element parentNode, String nodeName) {
		Element childNode = new Element(nodeName);
		parentNode.addContent(childNode);
		return childNode;
	}

	public static Element addChildNode(Element parentNode, String nodeName, String nodeText) {
		Element childNode = addChildNode(parentNode, nodeName);
		childNode.addContent(nodeText);
		return childNode;
	}

	public static void saveXMLDocumentToFile(Document xmlDoc, String FullFilename) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(FullFilename));
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

	public static Document loadXMLFile(URL xmlURL) {
		Document xmlDoc = null;
		SAXBuilder saxBuilder = new SAXBuilder();

		try {
			xmlDoc = saxBuilder.build(xmlURL);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return xmlDoc;
	}

	public static Document loadXMLFile(String xmlFilename) {
		Document xmlDoc = null;
		SAXBuilder saxBuilder = new SAXBuilder();

		try {
			xmlDoc = saxBuilder.build(xmlFilename);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return xmlDoc;
	}

	public static Document loadXMLFile(File xmlFile) {
		Document xmlDoc = null;
		SAXBuilder saxBuilder = new SAXBuilder();

		try {
			xmlDoc = saxBuilder.build(xmlFile);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return xmlDoc;
	}

	public static boolean isXmlStringValid(String xmlDocString) throws Exception {
		Document xmlDoc = null;
		SAXBuilder saxBuilder = new SAXBuilder();
		StringReader MyStringReader = new StringReader(xmlDocString);
		xmlDoc = saxBuilder.build(MyStringReader);
		return (xmlDoc != null); // => true or Exception;
	}

	public synchronized static Document loadXML(String xmlDocString) {
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

	public static String getXmlAsStr(Element Node) {

		String resultXmlString;
		XMLOutputter xmlOutputter = new XMLOutputter();

		Format myFormat;
		myFormat = xmlOutputter.getFormat();
		myFormat.setOmitEncoding(true);
		myFormat.setOmitDeclaration(true);
		xmlOutputter.setFormat(myFormat);

		try {
			resultXmlString = xmlOutputter.outputString(Node);
		} catch (RuntimeException e) {
			resultXmlString = null;
		}
		return resultXmlString;
	}

	public static String getXmlAsStr(Document xmlDoc) {
		return getXmlAsStr(xmlDoc.getRootElement());
	}

	public static String getXmlAsStr(Element Node, boolean pretty) {
		String resultXmlString;
		XMLOutputter xmlOutputter = new XMLOutputter();

		if (pretty) {
			xmlOutputter.setFormat(Format.getPrettyFormat());
		} else {
			xmlOutputter.setFormat(Format.getCompactFormat());
		}

		try {
			resultXmlString = xmlOutputter.outputString(Node);
		} catch (RuntimeException e) {
			resultXmlString = null;
		}
		return resultXmlString;
	}

	@SuppressWarnings("rawtypes")
	public static void mergeXmlDoc(Document xmlDocMergeDest, Document xmlDocMergeSource) {
		for (ListIterator it = xmlDocMergeSource.getRootElement().getChildren().listIterator(); it.hasNext();) {
			Element node = (Element) it.next();
			xmlDocMergeDest.getRootElement().addContent(node.clone());
		}
	}
}