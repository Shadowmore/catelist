package de.ar.catelist;

import junit.framework.TestCase;

import org.jdom2.Document;
import org.jdom2.Element;

import de.ar.tools.XMLTool;

public class Catelist_Tester extends TestCase {

	public void test() {

		Document doc = XMLTool.createEmptyXMLDoc("ROOT");
		Element elementRoot = doc.getRootElement();

		Element elementTest = XMLTool.addChildNode(elementRoot, "TEST");
		elementTest.setAttribute("attr", "val");
		elementTest.setText("testtext");

		XMLTool.saveXMLDocumentToFileWithIndent(doc, "testdoc.xml");
	}
}