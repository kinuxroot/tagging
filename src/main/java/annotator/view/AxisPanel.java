package annotator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import annotator.model.main.TermModel;
import annotator.util.ImageHelper;

public class AxisPanel extends JPanel {

	private static final long serialVersionUID = -4100063266789600010L;
	private String axis;
	private JPanel panel;

	public AxisPanel(String axis) {
		this.axis = axis;
		panel = new JPanel();
		//outer = new JPanel();
		panel.setPreferredSize(new Dimension(800, 300));
		//outer.setBorder(new TitledBorder(axis));
		JLabel l = new JLabel(axis + ":");
		add(l);
		
		JScrollPane pane = new JScrollPane(panel);
		pane.doLayout();
		pane.setPreferredSize(new Dimension(800, 80));
		add(pane);
	}

	public String getAxis() {
		return axis;
	}

	public JPanel getJPanel() {
		return panel;
	}

	/**
	 * 
	 */
	public void clear() {
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(false);
		}
	}

	public void addItem(final TermModel term) {
		final JLabel label = new JLabel(term.getName());
		String pt = "resource" + File.separator + "images" + File.separator
				+ "remove.png";
		Image icon = ImageHelper.createIconImages(pt);
		final JButton b = new JButton(new ImageIcon(icon));
		b.setPreferredSize(new Dimension(15, 15));
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				label.setVisible(false);
				b.setVisible(false);
				ImageHelper.getCurrentImageModel().removeTag(term);

			}
		});

		panel.add(label);
		panel.add(b);
		panel.updateUI();
	}

}
