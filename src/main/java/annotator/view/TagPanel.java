package annotator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import annotator.model.ModelLocator;

public class TagPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AxisPanel[] axisPanels;

	public TagPanel(MainWindow parent) {
		
		setBorder(new TitledBorder("Tags"));
		String[] axes = ModelLocator.getInstance().getAxisName();
		axisPanels = new AxisPanel[axes.length];
		int height = axes.length * 100;
		setPreferredSize(new Dimension(1000, height));

		FlowLayout layout = (FlowLayout) getLayout();
		layout.setVgap(-5);
		setLayout(layout);
		int i = 0;
		for (String axis : axes) {
			axisPanels[i] = new AxisPanel(axis);
			//JScrollPane pane = new JScrollPane(axisPanels[i]);
			add(axisPanels[i]);
			i++;
		}		

	}

	public AxisPanel getAxisPanelByName(String axis) {
		AxisPanel ap = null;
		for (AxisPanel p : axisPanels) {
			if (p.getAxis().equals(axis)) {
				ap = p;
			}
		}
		return ap;
	}

	/**
	 * 
	 */
	public void clear() {
		for (int i = 0; i < axisPanels.length; i++) {
			axisPanels[i].clear();
		}
		
	}

}
