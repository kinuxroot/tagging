package annotator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import annotator.controller.CurrentImages;
import annotator.model.ModelLocator;
import annotator.util.CSVHelper;

/**
 * @author Brandon B. Lin
 * 
 */
public class LoadMultiFileFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String DATA_FILE_PATH = "dataFilePath";

	private JFileChooser chooser;
	private JButton browse1;
	private JButton loadButton;
	private JTextField text1;
	private File dataFile;
	private Preferences preferences;

	public LoadMultiFileFrame() {
		setLocation(100, 100);
		setSize(300, 200);
		setLayout(new BorderLayout(10, 50));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String logo = "resource" + File.separator + "images" + File.separator
				 + "logo.png"; 
				ImageIcon img = new ImageIcon(logo);
				this.setIconImage(img.getImage());
		preferences = Preferences.userNodeForPackage(LoadFrame.class);
		// String path1 = preferences.get(DATA_FILE_PATH, "C:" +
		// File.separator);
		// String path2 = preferences.get(IMAGE_FILE_PATH, "C:" +
		// File.separator);

		initComponent();

	}

	private void initComponent() {
		chooser = new JFileChooser(new File("."));
		chooser.setFileFilter(new FileNameExtensionFilter("csv files", "csv"));
		chooser.setAcceptAllFileFilterUsed(false);

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("Choose the directory to load metedata"));
		text1 = new JTextField("C:" + File.separator, 20);
		panel1.add(text1);
		browse1 = new JButton("Browse");
		browse1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int state;

				state = chooser.showOpenDialog(null);
				dataFile = chooser.getSelectedFile();

				if (dataFile != null && state == JFileChooser.APPROVE_OPTION) {
					text1.setText(dataFile.getAbsolutePath());
					preferences.put(DATA_FILE_PATH, dataFile.getAbsolutePath());

				}

			}
		});
		panel1.add(browse1);
		add(panel1, BorderLayout.CENTER);

		JPanel panel3 = new JPanel();
		loadButton = new JButton("Load and Begin");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String[]> metadata = CSVHelper.readCSVFile(dataFile
						.getAbsolutePath());
				metadata.remove(0); // remove the header
				CurrentImages.getInstance(metadata).clear();
				CurrentImages ci = CurrentImages.getInstance(metadata);
				ModelLocator.getInstance().getMainWindow()
						.refresh(ci.getImageModelByIndex(0));
				LoadMultiFileFrame.this.setVisible(false);

			}
		});
		panel3.add(loadButton);
		add(panel3, BorderLayout.SOUTH);

	}

}
