package tagger.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import annotator.util.ImageHelper;

/**
 * @author Brandon B. Lin
 * 
 */
public class AxesPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private WelcomeFrame parent;
	private GridBagLayout layout;
	private JButton addButton;
	private ArrayList<JTextField> fields = new ArrayList<>();


	/* current row to add axis*/
	private int currentRow = 1;
	private int defBeginning = 0;
	

	private JTextField blankAxesField;
	private JTextField blankOntologyField;
	private JTextField axesField;
	private JTextField ontologyField;

	/**
	 * constructor with parent frame
	 * 
	 * @param parent
	 */
	public AxesPanel(WelcomeFrame parent) {
		this.parent = parent;
		layout = new GridBagLayout();
		setLayout(layout);
		setBackground(Color.YELLOW);
		initComponent();
	}

	/**
	 * init the panel
	 */
	private void initComponent() {
		add(new JLabel("Axes"),
				new GBC(0, 0, 1, 1).setAnchor(GridBagConstraints.CENTER));
		add(new JLabel("Enter BioPortal Ontologies"), new GBC(1, 0, 1, 1)
				.setAnchor(GridBagConstraints.WEST).setInsets(5, 5, 5, 30));

		addBlank();

		String pt = "resource" + File.separator + "images" + File.separator
				+ "add.jpg";
		ImageIcon icon = new ImageIcon(ImageHelper.createIconImages(pt));
		addButton = new JButton(icon);
		addButton.setPreferredSize(new Dimension(15, 15));
		addButton.addActionListener(this);
		addButton();

	}

	/**
	 * add blank row at the bottom
	 */
	public void addBlank() {
		blankAxesField = new JTextField("", 18);
		blankOntologyField = new JTextField("", 18);
		/*add blank axis below current row */
		add(blankAxesField, new GBC(0, currentRow , 1, 1).setInsets(3, 0, 3, 30));
		add(blankOntologyField,
				new GBC(1, currentRow , 1, 1).setInsets(3, 0, 3, 30));
		updateUI();
	}

	/**
	 * add addButton on the bottom
	 */
	public void addButton() {
		add(addButton,
				new GBC(1, currentRow + 1, 1, 1)
						.setAnchor(GridBagConstraints.EAST));
		updateUI();
	}
	
	/**
	 * add axis to the panel
	 * 
	 * @param axis
	 * @param ontology
	 */
	public void addAxis(String axis, String ontology) {
		
		parent.addAxes(axis, ontology);
		
		/*  remove blank and add axis below current row*/
		remove(blankAxesField);
		remove(blankOntologyField);
		
		axesField = new JTextField(axis, 18);
		ontologyField = new JTextField(ontology, 18);
		fields.add(axesField);
		fields.add(ontologyField);
		add(axesField, new GBC(0, currentRow , 1, 1).setInsets(3, 0, 3, 30));
		add(ontologyField,
				new GBC(1, currentRow , 1, 1).setInsets(3, 0, 3, 30));
		/* increase current row */
		currentRow++;
		
		addBlank();
		addButton();
		updateUI();
		//System.out.println("Add CURRENT: " + currentRow);
		
	}

	/**
	 * remove default axes
	 */
	public void removeDefaultAxes() {
	
		for (int k = defBeginning; k < defBeginning+12; k++) {
			remove(fields.get(k));

		}
		
		Collection<JTextField> temp =  fields.subList(defBeginning, defBeginning+12);
		fields.removeAll(temp);
		currentRow = (fields.size()/2) +1;
		updateUI();
		//System.out.println("Remove CURRENT: " + currentRow);

	}

	

	/**
	 * action for addButton
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		addAxis(blankAxesField.getText(), blankOntologyField.getText());
	}
	
	public int getDefBeginning() {
		return defBeginning;
	}
	
	/**
	 * set index where the default axes begin 
	 * @param defBeginning
	 */
	public void setDefBeginning(int defBeginning) {
		this.defBeginning = defBeginning;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<JTextField> getFields() {
		return fields;
	}


}
