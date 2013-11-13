package annotator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;

import annotator.model.main.AxisModel;
import annotator.model.main.ImageModel;
import annotator.model.main.OntologyModel;
import annotator.util.Ontology2IDHelper;
import annotator.view.MainWindow;

/**
 * @author Brandon B. Lin
 * 
 *         For universal Usage: Singleton
 */
public class ModelLocator {

	private static ModelLocator instance = null;
	/* main window fro the application */
	private MainWindow mainWindow = null;
	// name of axes
	private String[] axisName;
	// mapping axes to ontologies
	private HashMap<String, String> maps = new HashMap<String, String>();

	private ArrayList<AxisModel> axes;

	private ImageModel defaultModel = new ImageModel();
	private Preferences preferences;

	/**
	 * can't be initialized
	 */
	private ModelLocator() {
	}

	public void init() {
		preferences = Preferences.userNodeForPackage(ModelLocator.class);
		String title = preferences.get("TITLE", "Default figure");
		String figureFileName = preferences.get("FIGURE_FILE_NAME", "cell.png");
		String legend = preferences
				.get("LEGEND",
						"Panel A (periodic acid-Schiff stain) shows mesangial hypercellularity, with four or more cells per mesangial area (arrow). Panel B (periodic acid-Schiff stain) shows segmental endocapillary proliferation with occlusion of the capillary lumen (arrow). Panel C (periodic acid-Schiff stain) shows segmental glomerulosclerosis and adhesion, with focal accumulation of hyaline and obliteration of the capillary lumen (arrow). Panel D (trichrome stain) shows tubular atrophy and interstitial fibrosis, with severe interstitial scarring and loss of tubules (arrow). Panel E (periodic acid-Schiff stain) shows a glomerular crescent; a circumferential layer of epithelial cells surrounds the glomerular tuft (arrow). Panel F (immunofluorescence stain with fluorescein-conjugated anti-IgA antibodies) shows diffuse mesangial staining for IgA (arrow). In Panel G, an electron micrograph of a glomerular capillary tuft in a specimen fixed in osmium tetroxide shows electron-dense material in the mesangial area (arrow), a finding that is consistent with the accumulation of immune complexes.");
		defaultModel.setTitle(title);
		defaultModel.setFigureFileName(figureFileName);
		defaultModel.setLegend(legend);

		axes = createAxes();

	}

	/**
	 * create axes to use
	 * 
	 * @return
	 */
	private ArrayList<AxisModel> createAxes() {

		// local variable
		ArrayList<AxisModel> temp = new ArrayList<AxisModel>();

		for (int i = 0; i < axisName.length; i++) {
			AxisModel axis = new AxisModel();
			axis.setName(axisName[i]); // not completed
			temp.add(axis);
		}

		return associateAxesWithOntologies(temp);
	}

	/**
	 * @param axes
	 * @return
	 */
	private ArrayList<AxisModel> associateAxesWithOntologies(
			ArrayList<AxisModel> axes) {

		// SNOMEDCT
		OntologyModel ot1 = new OntologyModel();
		ot1.setOntologyName("SNOMEDCT");
		ot1.setVirtualId("1353");
		//NCIT
		OntologyModel ot2 = new OntologyModel();
		ot2.setOntologyName("NCIT");
		ot2.setVirtualId("1032");
		//RXNORM
		OntologyModel ot3 = new OntologyModel();
		ot3.setOntologyName("RXNORM");
		ot3.setVirtualId("1423");
		//GO
		OntologyModel ot4 = new OntologyModel();
		ot4.setOntologyName("GO");
		ot4.setVirtualId("1070");

		
		ArrayList<AxisModel> newAxisModels = new ArrayList<AxisModel>();
		
		for (AxisModel am : axes) {
			String name = am.getName();
			OntologyModel o = new OntologyModel();
			ArrayList<OntologyModel> temp = new ArrayList<OntologyModel>();
			temp = am.getOntologies();
			if (name.equals("All")) {
				temp.add(ot1);
				temp.add(ot2);
				temp.add(ot3);
				temp.add(ot4); // All
			} else if (name.equals("Recommended")) {
				// null
			} else if (name.equals("BioPortal")) {
				// null let search param empty
			} else {
				String ontologyName = maps.get(name);
				//System.out.println("NAME: " + ontologyName);
				String ontologyId = Ontology2IDHelper.ontology2IDMap
						.get(ontologyName);
				//System.out.println("ID: " + ontologyId);
				o.setOntologyName(ontologyName);
				o.setVirtualId(ontologyId);
				temp.add(o);
			}
			
			am.setOntologies(temp);
			newAxisModels.add(am);

		}
		
		/*for(AxisModel t: newAxisModels) {
			String r = t.getOntologies().size()>0 ? t.getOntologies().get(0).getVirtualId(): "NONE";
			//System.out.println(t.getName() + ": " + r );
		}
*/
		return newAxisModels;
	}

	
	/**
	 * get instance of ModelLocator singleton
	 * 
	 * @return
	 */
	public static ModelLocator getInstance() {
		if (instance == null) {
			instance = new ModelLocator();
		}
		return instance;
	}
	
	public ArrayList<AxisModel> getAxes() {
		return axes;
	}

	public ImageModel getDefaultImageModel() {
		return defaultModel;
	}

	// main window
	public void setMainWindow(MainWindow argWindow) {
		mainWindow = argWindow;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	// preference
	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	// axes name
	public String[] getAxisName() {
		return axisName;
	}

	public void setAxisName(String[] axisName) {
		this.axisName = axisName;
	}

	// maps
	public HashMap<String, String> getMaps() {
		return maps;
	}

	public void setMaps(HashMap<String, String> maps) {
		this.maps = maps;
	}

}
