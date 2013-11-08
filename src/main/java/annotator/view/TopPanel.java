package annotator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import annotator.model.ModelLocator;
import annotator.model.main.ImageModel;

public class TopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private LeftPanel left;
	private MiddlePanel middle;
	private RightPanel right;
	private ModelLocator locator = ModelLocator.getInstance();

	public TopPanel(MainWindow parent) {
		setPreferredSize(new Dimension(1000, 350));
		setBorder(new TitledBorder(""));
		setLayout(new BorderLayout());
		initComponent();
	}

	private void initComponent() {
		left = new LeftPanel();
		add(left, BorderLayout.WEST);

		right = new RightPanel();
		add(right, BorderLayout.EAST);

		middle = new MiddlePanel(right);
		add(middle, BorderLayout.CENTER);
	}

	/**
	 * @param image
	 */
	public void refresh(ImageModel image) {
		String url;
		String type = image.getType();
		System.out.println("Test in TopPanel: " + type);

		if (type.equals("picture")) {
			url = locator.getPreferences().get("IMAGE_FILE_PATH", "")
					+ File.separator + image.getFigureFileName();
		} else {
			url = image.getFigureFileName();
		}

		try {
			left.update(type, image.getTitle(), url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		middle.refresh(image.getLegend());
		right.clear();

	}

}
