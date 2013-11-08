package annotator.model.main;

import java.net.URL;

/**
 * @author Brandon B. Lin
 * 
 */
public class TermModel {

	private String name;
	private String seachTerm;
	/* which ontology it belong to */
	private OntologyModel ontology;
	private AxisModel axis;
	private String level;
	private URL fullId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OntologyModel getOntology() {
		return ontology;
	}

	public void setOntology(OntologyModel ontology) {
		this.ontology = ontology;
	}

	public String getSeachTerm() {
		return seachTerm;
	}

	public void setSeachTerm(String seachTerm) {
		this.seachTerm = seachTerm;
	}

	public AxisModel getAxis() {
		return axis;
	}

	public void setAxis(AxisModel axis) {
		this.axis = axis;
	}

	

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public URL getFullId() {
		return fullId;
	}

	public void setFullId(URL fullId) {
		this.fullId = fullId;
	}
	

	
}
