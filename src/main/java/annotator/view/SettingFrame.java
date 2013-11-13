package annotator.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import tagger.views.GBC;

/**
 * show setting when clik view menu
 * 
 * @author Brandon B. Lin
 * 
 */
public class SettingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private HashMap<String, String> axes2Ontologies = new HashMap<String, String>();
	private boolean checkBioPortal = false;
	private GridBagLayout layout;
	private JTextField axesField;
	private JTextField ontologyField;
	private int row = 1;

	public SettingFrame(HashMap<String, String> axes2Ontologies,
			boolean checkBioPortal) {
		this.axes2Ontologies = axes2Ontologies;
		this.checkBioPortal = checkBioPortal;
		initComponent();
	}

	private void initComponent() {
		layout = new GridBagLayout();
		setLayout(layout);
		setBackground(Color.YELLOW);
		setLocation(100, 100);
		setSize(600, 400);

		String logo = "resource" + File.separator + "images" + File.separator
				+ "logo.png";
		ImageIcon img = new ImageIcon(logo);
		this.setIconImage(img.getImage());

		add(new JLabel("Axes"),
				new GBC(0, 0, 1, 1).setAnchor(GridBagConstraints.CENTER));
		add(new JLabel("BioPortal Ontologies"), new GBC(1, 0, 1, 1)
				.setAnchor(GridBagConstraints.WEST).setInsets(5, 5, 5, 30));
		
		@SuppressWarnings("rawtypes")
		Iterator iter = axes2Ontologies.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			boolean test = key.equals("All") || key.equals("Recommended") || key.equals("BioPortal");
			if (!test) {
				addRow(key, val);
				row++;
			}

		}

		JCheckBox checkBox = new JCheckBox("Use Bioportal");
		checkBox.setSelected(checkBioPortal);
		checkBox.setEnabled(false);
		add(checkBox, new GBC(0, row, 1, 1).setInsets(3, 0, 3, 30));

	}

	private void addRow(String axis, String ontology) {
		axesField = new JTextField(axis, 18);
		axesField.setEditable(false);
		ontologyField = new JTextField(ontology, 18);
		ontologyField.setEditable(false);
		add(axesField, new GBC(0, row, 1, 1).setInsets(3, 0, 3, 30));
		add(ontologyField, new GBC(1, row, 1, 1).setInsets(3, 0, 3, 30));
	}

}
