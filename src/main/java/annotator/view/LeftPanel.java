package annotator.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import annotator.controller.Options;
import annotator.model.ModelLocator;
import annotator.model.main.ImageModel;
import annotator.model.widget.MyJTextArea;
import annotator.util.ImageHelper;

public class LeftPanel extends JPanel {
	/**
	 * Display the picture with caption and legend
	 */
	private static final long serialVersionUID = 1L;

	private String imagePath;
	private MyJTextArea titleArea;
	private JLabel imageJLabel;

	private ModelLocator locator = ModelLocator.getInstance();
	private ImageModel imageModel;

	public LeftPanel() {
		setPreferredSize(new Dimension(350, 350));
		setLayout(new BorderLayout());
		// setBorder(new TitledBorder("Current Image"));

		imageModel = locator.getDefaultImageModel();
		imageModel.getTitle();
		imagePath = "resource" + File.separator + "images" + File.separator
				+ imageModel.getFigureFileName();
		addPanels();
	}

	private void addPanels() {

		titleArea = new MyJTextArea();
		titleArea.setSize(300, 30);
		titleArea.setLineWrap(true);
		titleArea.setEditable(false);
		titleArea.setText("Title");
		//JScrollPane pane = new JScrollPane(titleJLabel);
		add(titleArea, BorderLayout.NORTH);

		imageJLabel = new JLabel();
		imageJLabel.setSize(400, 300);
		imageJLabel.setIcon(ImageHelper.getImageIcon(imagePath, 400, 300));
		/* Zoom In */
		imageJLabel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		imageJLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int size = Options.sizeOfLargeImage;
				if (arg0.getClickCount() == 2) {
					ImageIcon icon = ImageHelper.getImageIcon(imagePath, size,
							size);
					LargeImageFrame large = new LargeImageFrame(icon, size);
					large.setVisible(true);
				}

				if (java.awt.Desktop.isDesktopSupported()) {
					try {
						// instantia URI
						java.net.URI uri = java.net.URI.create(imagePath);
						// desktop
						java.awt.Desktop dp = java.awt.Desktop.getDesktop();
						// if supported
						if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {

							dp.browse(uri);
						}
					} catch (java.lang.NullPointerException e1) {
						// uri is empty
					} catch (java.io.IOException e2) {
						// can't find browser
					}
				}

			}
		});

		add(imageJLabel, BorderLayout.CENTER);
	}

	public boolean update(String type, String title, final String url)
			throws IOException {
		titleArea.setText(title);
		this.imagePath = url;
		if (type.equals("picture")) {
			imageJLabel.setIcon(ImageHelper.getImageIcon(imagePath, 400, 300));
			System.out.println("I am a picture");
		} else {
			System.out.println("I am NOT a picture");
			imageJLabel.setIcon(null);
			imageJLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			String hint = "<html> Click here to see a " + type
					+ " from the article " + title + " </html>";
			imageJLabel.setText(hint);
		}
		updateUI();
		return true;
	}

}
