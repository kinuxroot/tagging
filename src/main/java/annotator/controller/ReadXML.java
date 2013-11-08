package annotator.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import annotator.model.main.OntologyModel;
import annotator.model.main.TermModel;
import annotator.util.XMLHelper;

public class ReadXML {

	/* results to exclude */
	private static final String[] TERMSTOEXCLUDE = { "Conceptual Entity",
			"Ambiguous Concept", "Property or Attribute", "General Qualifier",
			"Anatomy Qualifier", "Action",
			"Adverse Event Associated with Pain",
			"General Adjectival Modifier", "Unapproved Attribute",
			"Spatial Qualifier", "Retired Concept (any year)",
			"Instruction Imperative", "Person", "Level", "Story", "Have",
			"Associated with", "Feature", "Patient", "Level", "Quantity",
			"Navigational Concept", "Order Values",
			"Other Organismal Grouping", "General Information Qualifier",
			"Reason not stated concept", "General site descriptor", "Due to",
			"Continue", "Characteristic", "No evidence of", "Is a",
			"Ranked categories", "Attribute", "Event",
			"Other Anatomic concept", "Sub-location", "Duplicate concept" };

	public static ArrayList<TermModel> extractTerms(File xmlFile) {
		ArrayList<TermModel> results = new ArrayList<>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			/* getDocumentElement return root element */
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("annotationBean");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				TermModel term = new TermModel();

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode; // a annotationBean node

					/* concept list within annotationBean ,actually only 1 */
					List<Element> concepts = XMLHelper.getChildrenByTagName(
							eElement, "concept");
					/* concept node */
					Element conceptElement = concepts.get(0);

					String name = conceptElement
							.getElementsByTagName("preferredName").item(0)
							.getTextContent();
					String fullId = conceptElement
							.getElementsByTagName("fullId").item(0)
							.getTextContent();
					URL url = new URL(fullId);
					String localOntologyId = conceptElement
							.getElementsByTagName("localOntologyId").item(0)
							.getTextContent();
					OntologyModel ontology = new OntologyModel();
					ontology.setVirtualId(localOntologyId);

					List<Element> contexts = XMLHelper.getChildrenByTagName(
							eElement, "context");
					Element contextElement = contexts.get(0);

					String level = contextElement
							.getElementsByTagName("isDirect").item(0)
							.getTextContent();
					term.setLevel(level.equals("0") ? "Direct" : "Ancestor");
					term.setName(name);
					term.setFullId(url);
					term.setOntology(ontology);
				}
				if (!contain(term.getName(), TERMSTOEXCLUDE)) {
					results.add(term);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * if a String exist in a String array
	 * 
	 * @param element
	 * @param array
	 * @return
	 */
	private static boolean contain(String element, String[] array) {
		boolean result = false;
		for (String e : array) {
			if (e.equals(element)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
