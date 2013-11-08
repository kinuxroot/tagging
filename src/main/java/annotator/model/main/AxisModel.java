package annotator.model.main;

import java.util.ArrayList;

/**
 * @author Brandon B. Lin
 * 
 */
public class AxisModel {

	private ArrayList<OntologyModel> ontologies = new ArrayList<>();

	private String name;

	public ArrayList<OntologyModel> getOntologies() {
		return ontologies;
	}

	public void setOntologies(ArrayList<OntologyModel> ontologies) {
		this.ontologies = ontologies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
