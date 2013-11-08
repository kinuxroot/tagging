package annotator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import annotator.controller.CurrentImages;
import annotator.controller.Options;
import annotator.controller.ReadXML;
import annotator.controller.Search;
import annotator.model.ModelLocator;
import annotator.model.main.AxisModel;
import annotator.model.main.OntologyModel;
import annotator.model.main.TermModel;
import annotator.model.widget.MyJTextArea;
import annotator.model.widget.MyJTextField;
import annotator.util.StringHelper;
import annotator.util.go.Prepare4Go;
import annotator.util.go.TransObo2Map;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Brandon B. Lin
 * 
 */
public class MiddlePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private MyJTextArea legendArea;
	private String legend;
	private JTextField termField;
	private JComboBox<String> axisBox;
	private JButton searchButton;
	private JButton recommendButton;
	private ArrayList<AxisModel> axes;
	private String recommendOntologiesId = "1353,1070,1032,1423"; // should
																	// change as
																	// user

	/* the object to change by butttons */
	private RightPanel right;
	private TagPanel tagPanel;
	/* search results */
	private ArrayList<TermModel> matches = new ArrayList<>();

	/**
	 * constructor
	 * 
	 * @param right
	 */
	public MiddlePanel(RightPanel right) {
		this.right = right;
		/* settings of middlw panel */
		setPreferredSize(new Dimension(300, 350));
		BorderLayout layout = new BorderLayout();
		layout.setHgap(5);
		layout.setVgap(10);
		setLayout(layout);

		initComponent();

	}

	/**
	 * initialize the components
	 */
	private void initComponent() {

		// -------------legend section ------------------//
		legend = ModelLocator.getInstance().getDefaultImageModel().getLegend();
		legendArea = new MyJTextArea();
		legendArea.setSize(300, 30);
		legendArea.setLineWrap(true);
		legendArea.setEditable(false);
		legendArea.setText(legend);
		JScrollPane pane = new JScrollPane(legendArea);
		pane.doLayout();
		pane.setPreferredSize(new Dimension(300, 140));
		add(pane, BorderLayout.NORTH);

		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new TitledBorder("Search"));
		// ---------- search box-----------------//
		termField = new MyJTextField();
		termField.setColumns(23);
		// empty when get focus
		termField.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				termField.setText("");
			}
		});

		searchPanel.add(termField);

		// --------------drop-down box --------------------//
		String[] axisName = ModelLocator.getInstance().getAxisName();
		final String[] items = axisName;
		axes = ModelLocator.getInstance().getAxes();
		axisBox = new JComboBox<>(items);
		axisBox.setPreferredSize(new Dimension(260, 28));
		searchPanel.add(axisBox);

		// --------------- search button ------------------//
		searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(110, 28));
		searchButton.addActionListener(this);
		searchPanel.add(searchButton);

		// ---------------- recommend button --------------//
		recommendButton = new JButton("Recommeded Tags");
		recommendButton.addActionListener(this);
		searchPanel.add(recommendButton);
		add(searchPanel, BorderLayout.CENTER);

	}

	/**
	 * refresh the panel, ie. legend of figure
	 * 
	 * @param legend2
	 */
	public void refresh(String legend) {
		this.legend = legend;
		legendArea.setText(this.legend);
		updateUI();
	}

	/**
	 * actions for serach and recommend buttons
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		switch (source) {
		case "Search":
			doSearch();
			break;

		case "Recommeded Tags":
			doRecommend();
			break;

		default:
			break;
		}

	}

	/**
	 * search action
	 */
	private void doSearch() {

		String term = termField.getText();
		int selectedIndex = axisBox.getSelectedIndex();
		AxisModel axis = axes.get(selectedIndex);
		
		// Go should be searched in paticularlly
		if (axis.getName().equals("Genetics, Proteins & Processes")) {
			// ----------------------Go search begin-------------------
			//System.out.println("Search GO ");
			
			matches.removeAll(matches);

			/* map species to matched Go ids
			 * specie <---> matched ids */
			HashMap<String, ArrayList<String>> maps = new HashMap<>(); 
			ArrayList<String> matchedGoIds = new ArrayList<>(); // id list
																// (GO:0005634 )
			ArrayList<String> species = Prepare4Go.SPECIES; // 
			// search all ontology files (csv)
			for (String specie : species) {
				matchedGoIds = searchWithinAnOntologyFile(term, specie);
				maps.put(specie, matchedGoIds);
			}

			OntologyModel goOntologyModel = new OntologyModel();
			goOntologyModel.setOntologyName("GO");
			goOntologyModel.setVirtualId("1070");
			// through hashmap
			Iterator<Entry<String, ArrayList<String>>> iter = maps.entrySet()
					.iterator();
			while (iter.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iter.next();
				// Object key = entry.getKey();
				@SuppressWarnings("unchecked")
				ArrayList<String> ids = (ArrayList<String>) entry.getValue(); // matched
																				// ids
																				// of
																				// a
																				// specie
				String spiece = (String) entry.getKey();

				for (String s : ids) {
					TermModel termModel = new TermModel();
					String r = "id: " + s;
					String name = TransObo2Map.goId2Name.get(r);// get name by
																// id
					if (name != null) {
						termModel.setName(name + "(" + spiece + ")");
						termModel.setSeachTerm(term);
						String temp = "http://purl.obolibrary.org/obo/GO_"
								+ s.substring(3, s.length());
						URL url = null;
						try {
							url = new URL(temp);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						termModel.setFullId(url);
						termModel.setAxis(axis);
						termModel.setLevel("Direct");
						termModel.setOntology(goOntologyModel);
						matches.add(termModel);
					}

				}

			}
			// -----------------------GO search end---------------------------

		} else { // not GO, just search as usually
			//*****************************************************//
			//System.out.println("Search by axis: " + axis.getName());
			matches.removeAll(matches);
			// get ontologies' ids of axis
			String[] ontologyIds = new String[axis.getOntologies().size()];
			int i = 0;
			for (OntologyModel o : axis.getOntologies()) {
				//System.out.println("ONTOLOGY: " + o.getOntologyName());
				
				ontologyIds[i] = o.getVirtualId();
				//System.out.println("ONTOLOGY ID: " + ontologyIds[i]);
				i++;
			}
			//System.out.println("Ontology Ids: " + ontologyIds[0]);

			String filePath = Options.savePath4Search + term + ".xml";
			File resultFile = Search.searchByTerm(term,
					StringHelper.covStringArrayToString(ontologyIds), filePath);
			// all terms returned by search
			matches = ReadXML.extractTerms(resultFile); // term model list
			for (TermModel m : matches) {
				m.setAxis(axis);
				m.setSeachTerm(term); // complete the term's content
			}

		}

		// refresh the UI
		right.clearSearchResult();
		ResultPanel resultPanel = right.getResultPanel();
		tagPanel = ModelLocator.getInstance().getMainWindow().getTagPanel();
		System.out.println("result size: " + matches.size());
		resultPanel.refresh(tagPanel.getAxisPanelByName(axis.getName()),
				axis.getName(), matches);
		resultPanel.updateUI();

	}

	/**
	 * search within an ontology file
	 * 
	 * @param term
	 *            : search term
	 * @param species
	 *            : ontology file
	 * @return Go ids
	 */
	private ArrayList<String> searchWithinAnOntologyFile(String term,
			String species) {
		String csvPath = Prepare4Go.PATHTOSAVEGOCSV + File.separator + species
				+ ".csv"; // csv path to search
		ArrayList<String> result = new ArrayList<>(); // id results
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(csvPath));
			List<String[]> list = reader.readAll();
			// System.out.println("list size: " + list.size());

			for (String[] rd : list) {
				if (rd.length >= 4) {
					if (rd[2].equals(term)) {
						result.add(rd[3]); // add 3rd column if found
					}
				}

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * recommend action
	 */
	private void doRecommend() {

		String term = legend;
		CurrentImages ci = CurrentImages.getInstance(null);
		String figureName = ci.getImageModelByIndex(ci.getCurrent())
				.getFigureFileName();
		String filePath = Options.savePath4Recommend + figureName + ".xml";
		File resultFile = Search.searchByTerm(term, recommendOntologiesId,
				filePath);
		/* be careful: two matches for search and recommend */
		matches = ReadXML.extractTerms(resultFile);
		String axis = "Recommended";
		for (TermModel m : matches) {
			AxisModel a = new AxisModel();
			a.setName(axis);
			m.setAxis(a);
		}
		File csvFile = new File(Options.savePath4Recommend + figureName
				+ ".csv");
		saveRecommendResultAsCsv(matches, csvFile);
		right.clearRecommendedResult();
		ResultPanel recommendedPanel = right.getRecommendedPanel();
		tagPanel = ModelLocator.getInstance().getMainWindow().getTagPanel();
		recommendedPanel.refresh(tagPanel.getAxisPanelByName(axis), axis,
				matches);
		recommendedPanel.updateUI();
	}

	/**
	 * save the recommendation result as csv
	 */
	private void saveRecommendResultAsCsv(ArrayList<TermModel> recomendResult,
			File file) {
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(file));

			List<String[]> data = new ArrayList<String[]>();
			data.add(new String[] { "TermOfInterest", "Axis", "MatchedTerm",
					"FullId", "MatchType", "ontology" });
			for (TermModel term : recomendResult) {
				String termName = term.getName();
				// String termFromLegend = term.getSeachTerm();
				String ontology = term.getOntology().getVirtualId();
				String fullId = term.getFullId().toString();
				String matchType = term.getLevel();
				String axis = term.getAxis().getName();
				String[] temp = { termName, axis, termName, fullId, matchType,
						ontology };
				data.add(temp);
			}
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
