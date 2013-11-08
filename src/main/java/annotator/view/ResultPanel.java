package annotator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import annotator.model.main.TermModel;
import annotator.util.ImageHelper;

public class ResultPanel extends JPanel {

	private static final long serialVersionUID = -656393635345201985L;

	/**
	 * constructor
	 * @param borderTitle
	 */
	public ResultPanel(String borderTitle) {
		this.setBorder(new TitledBorder(borderTitle));
	}

	/**
	 * refresh the panel after clicking search button
	 * 
	 * @param axisPanel
	 * @param axis
	 * @param matches
	 */
	public void refresh(AxisPanel axisPanel, String axis, ArrayList<TermModel> matches) {

		if (matches.size() > 0) {
			for (int i = 0; i < matches.size(); i++) {
				addItem(axisPanel, matches.get(i));
			}
		} else {
			add(new JLabel("No matched result"));
		}

	}

	/**
	 * add item to result panel
	 * @param axisPanel
	 * @param match
	 */
	private void addItem(final AxisPanel axisPanel, final TermModel match) {
		final JLabel label = new JLabel("<html>" + match.getName() + "</html>");
		add(label);
		final JLabel sl = new JLabel(" | ");

		String pt1 = "resource" + File.separator + "images" + File.separator
				+ "add.jpg";
		String pt2 = "resource" + File.separator + "images" + File.separator
				+ "remove.jpg";
		ImageIcon icon = new ImageIcon(ImageHelper.createIconImages(pt1));
		ImageIcon icon1 = new ImageIcon(ImageHelper.createIconImages(pt2));
		final JButton addButton = new JButton(icon);
		final JButton removeButton = new JButton(icon1);

		addButton.setPreferredSize(new Dimension(15, 15));
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				axisPanel.addItem(match);
				ImageHelper.getCurrentImageModel().addTag(match);

			}
		});

		add(addButton);

		removeButton.setPreferredSize(new Dimension(15, 15));
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				label.setVisible(false);
				addButton.setVisible(false);
				removeButton.setVisible(false);
				sl.setVisible(false);
				ResultPanel.this.updateUI();

			}
		});
		add(removeButton);
		add(sl);
	}


}
