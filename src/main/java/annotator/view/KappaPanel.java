package annotator.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tagger.views.GBC;

/**
 * to show kappa values
 * 
 * @author Brandon B. Lin
 *
 */
public class KappaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagLayout layout;
	private JLabel[] valueLabels = new JLabel[4];
	
	public KappaPanel() {
		layout = new GridBagLayout();
		setLayout(layout);
		initComponent();
	}
	
	private void initComponent() {
		add(new JLabel("Rater1 & Recommender: "),
				new GBC(0, 0, 1, 1).setAnchor(GridBagConstraints.CENTER));
		valueLabels[0] = new JLabel("none");		
		add(valueLabels[0], new GBC(1, 0, 1, 1)
				.setAnchor(GridBagConstraints.WEST).setInsets(5, 5, 5, 30));
		
		add(new JLabel("Rater2 & Recommender: "),
				new GBC(0, 1, 1, 1).setAnchor(GridBagConstraints.CENTER));
		valueLabels[1] = new JLabel("none");		
		add(valueLabels[1], new GBC(1, 1, 1, 1)
				.setAnchor(GridBagConstraints.WEST).setInsets(5, 5, 5, 30));
		
		add(new JLabel("Rater1 & Rater2 (Terms): "),
				new GBC(0, 2, 1, 1).setAnchor(GridBagConstraints.CENTER));
		valueLabels[2] = new JLabel("none");		
		add(valueLabels[2], new GBC(1, 2, 1, 1)
				.setAnchor(GridBagConstraints.WEST).setInsets(5, 5, 5, 30));
		
		add(new JLabel("Rater1 & Rater2 (FullId): "),
				new GBC(0, 3, 1, 1).setAnchor(GridBagConstraints.CENTER));
		valueLabels[3] = new JLabel("none");		
		add(valueLabels[3], new GBC(1, 3, 1, 1)
				.setAnchor(GridBagConstraints.WEST).setInsets(5, 5, 5, 30));
		
		for (JLabel label : valueLabels) {
			label.setForeground(Color.RED);
		}
		
	}
	
	public void showValue(int index, double d) {
		valueLabels[index].setText("" + d);
		updateUI();
	}

}
