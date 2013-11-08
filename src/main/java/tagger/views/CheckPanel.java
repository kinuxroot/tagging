package tagger.views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tagger.models.Axes;
import annotator.model.ModelLocator;
import annotator.model.main.ImageModel;
import annotator.util.StringHelper;
import annotator.view.MainWindow;

/**
 * @author Brandon B. Lin
 * 
 */
public class CheckPanel extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private final String TEXT1 = "Use default Axes and Ontologies";
	private final String TEXT2 = "Use entire Bioportal base for Annotations?";
	private JCheckBox defaultCheckBox;
	private JCheckBox bioPortalCheckBox;

	private JButton nextButton;
	private GridBagLayout layout;
	private WelcomeFrame parent;

	/**
	 * @param parent
	 */
	public CheckPanel(WelcomeFrame parent) {
		this.parent = parent;
		setSize(600, 100);
		setOpaque(true);
		setBackground(Color.YELLOW);
		layout = new GridBagLayout();
		setLayout(layout);
		initComponent();
	}

	/**
	 * init component
	 */
	private void initComponent() {
		defaultCheckBox = new JCheckBox(TEXT1);
		defaultCheckBox.setBackground(Color.YELLOW);
		defaultCheckBox.addItemListener(this);
		add(defaultCheckBox,
				new GBC(1, 0, 2, 1).setAnchor(GridBagConstraints.WEST));

		bioPortalCheckBox = new JCheckBox(TEXT2);
		bioPortalCheckBox.setBackground(Color.YELLOW);
		bioPortalCheckBox.addItemListener(this);
		add(bioPortalCheckBox,
				new GBC(1, 1, 2, 1).setAnchor(GridBagConstraints.WEST));

		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		add(nextButton, new GBC(3, 2, 1, 1).setAnchor(GridBagConstraints.EAST));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Next")) {
			nextAction();
		}

	}

	/*
	 * listen to the actions of checkbox
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox selectedBox = (JCheckBox) e.getItem();
		int state = e.getStateChange();
		if (selectedBox == defaultCheckBox) {
			defaultAction(state);
		} else {
			bioPortalAction(state);
		}

	}

	/**
	 * for default checkbox
	 */
	private void defaultAction(int state) {
		AxesPanel panel = parent.getAxesPanel();
		if (state == ItemEvent.DESELECTED) {
			panel.removeDefaultAxes();

		} else {// selected
			String[] axes = Axes.axes;
			String[] ontologies = Axes.ontologies;
			panel.setDefBeginning(panel.getFields().size());
			for (int i = 0; i < 6; i++) {
				panel.addAxis(axes[i], ontologies[i]);
			}

		}

	}

	/**
	 * action for bioPortal checkbox
	 */
	private void bioPortalAction(int state) {
		if (state == ItemEvent.DESELECTED) {
			parent.getAxes2Ontology().remove("BioPortal");

		} else {// selected
			parent.getAxes2Ontology().put("BioPortal", "");
		}

	}

	/**
	 * action for next button
	 */
	private void nextAction() {
		ModelLocator locator = ModelLocator.getInstance();

		/* map axes to ontologies */
		HashMap<String, String> axesMap = parent.getAxes2Ontology();
		/* add 2 axes : All & Recommended */
		axesMap.put("All", "");
		axesMap.put("Recommended", ""); // shouldn't appear on drop-down menu 
		String[] axesName = {};
		locator.setMaps(axesMap);
		locator.setAxisName(StringHelper.sortByPOPO(axesMap.keySet().toArray(
				axesName)));
		
		locator.init();

		ImageModel defaultImageModel = locator.getDefaultImageModel();
		MainWindow window = new MainWindow(parent, defaultImageModel);
		locator.setMainWindow(window);
		window.setSize(1000, 700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		parent.setVisible(false);
	}
	
	public JCheckBox getBioPortalCheckBox() {
		return bioPortalCheckBox;
	}

}
