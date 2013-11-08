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

public class LoadFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String DATA_FILE_PATH = "dataFilePath";
	private static final String IMAGE_FILE_PATH = "iamgeFilepath";

	private JFileChooser chooser;
	private JFileChooser chooser2;
	private JButton browse1;
	private JButton browse2;
	private JButton loadButton;
	private JTextField text1;
	private JTextField text2;

	private File dataFile;
	private File imageFile;
	private Preferences preferences;

	/**
	 * constructor
	 */
	public LoadFrame() {
		setLocation(100, 100);
		setSize(400, 300);
		setLayout(new BorderLayout(5, 50));
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

				if (dataFile != null && state == JFileChooser.APPROVE_OPTION) { // 选择了文件并点击了打开可保存按钮
					text1.setText(dataFile.getAbsolutePath());
					preferences.put(DATA_FILE_PATH, dataFile.getAbsolutePath());

				}

			}
		});
		panel1.add(browse1);

		panel1.add(new JLabel("Choose the directory to load images"));
		text2 = new JTextField("C:" + File.separator, 20);
		panel1.add(text2);
		chooser2 = new JFileChooser(new File("."));
		chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser2.setAcceptAllFileFilterUsed(false);

		browse2 = new JButton("Browse");
		browse2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int state;
				state = chooser2.showOpenDialog(null);
				if (state == JFileChooser.APPROVE_OPTION) {
					imageFile = chooser2.getSelectedFile();
					String temp = imageFile.getAbsolutePath();
					text2.setText(temp);
					ModelLocator.getInstance().getPreferences()
							.put("IMAGE_FILE_PATH", temp);
					preferences.put(IMAGE_FILE_PATH, temp);
				} else {
					System.out.println("wrong");
				}

			}
		});

		panel1.add(browse2);

		add(panel1, BorderLayout.CENTER);

		JPanel panel3 = new JPanel();
		loadButton = new JButton("Load and Begin");
		loadButton.addActionListener(this);
		panel3.add(loadButton);
		add(panel3, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ArrayList<String[]> metadata = CSVHelper.readCSVFile(dataFile
				.getAbsolutePath());
		metadata.remove(0); // header

		CurrentImages.getInstance(metadata).clear();
		CurrentImages ci = CurrentImages.getInstance(metadata);
		ModelLocator.getInstance().getMainWindow()
				.refresh(ci.getImageModelByIndex(0));
		LoadFrame.this.setVisible(false);

	}

}
