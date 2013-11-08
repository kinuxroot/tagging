package annotator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import annotator.controller.CurrentImages;
import annotator.controller.Kappa;
import annotator.controller.Options;
import annotator.model.main.ImageModel;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Brandon B. Lin
 * 
 */
public class ImportFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private KappaPanel kappaPanel;
	private JButton calculateButton;
	private JButton integrateButton;
	private File file1;
	private File file2; // import
	private File recommended;


	/**
	 * constructor
	 * @param otherFile
	 */
	public ImportFrame(File otherFile) {
		
		CurrentImages ci = CurrentImages.getInstance(null);
		ImageModel image = ci.getImageModelByIndex(ci.getCurrent());
		String annotationFilePath  = Options.savePath4Annotation + image.getFigureFileName() + ".csv";
		file1 = readAnnotationFileByPath(annotationFilePath);
		String recommendedAnnotationFilePath =  Options.savePath4Recommend + image.getFigureFileName() + ".csv";
		recommended = readAnnotationFileByPath(recommendedAnnotationFilePath);
		this.file2 = otherFile;
		setTitle("Kappa calculation");
		setLocation(200, 200);
		setSize(500, 300);
		initComponent();
		addListeners();

	}

	
	/**
	 * @param path
	 * @return
	 */
	private File readAnnotationFileByPath(String path) {
		return new File(path);
	}

	/**
	 * init frame
	 */
	private void initComponent() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		label.setText( "Click buttons to calculate the Kappa statistics and integrate imported file to yours"
				);
		panel.add(label);
		add(panel, BorderLayout.NORTH);
		
		kappaPanel = new KappaPanel();
		add(kappaPanel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		calculateButton = new JButton("Calculate Kappa statistics");
		buttonsPanel.add(calculateButton);

		integrateButton = new JButton("Integrate imported File");
		buttonsPanel.add(integrateButton);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	/**
	 * add listeners to buttons
	 */
	private void addListeners() {
		calculateButton.addActionListener(this);
		integrateButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equals("Calculate Kappa statistics")) {
			calAndShowKappa();
		} else { // Integrate imported File
			boolean state = integrateFiles(file1, file2);
			if (state == true) {
				JOptionPane.showConfirmDialog(null,
						"Integrate Successfully! ", "Integrate",
						JOptionPane.CLOSED_OPTION);

			}
		}
		
	}
	
	/**
	 * calculate kappa and show them
	 */
	private void calAndShowKappa() {
		/*calculate kappa here*/
		
		double kappa1 = Kappa.calKappaOfTermOfInterest(file1, recommended);
		double kappa2 = Kappa.calKappaOfTermOfInterest(file2, recommended);
		double kappa3 = Kappa.calKappaOfTermOfInterest(file1, file2);
		double kappa4 = Kappa.calKappaOfMatchedTerm(file1, file2);
		
		kappaPanel.showValue(0, kappa1);
		kappaPanel.showValue(1, kappa2);
		kappaPanel.showValue(2, kappa3);
		kappaPanel.showValue(3, kappa4);
	}
	
	/**
	 * integrate annotation files
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 */
	private boolean integrateFiles(File file1, File file2) {
		CSVReader reader;
		CSVWriter writer;
		try {
			reader = new CSVReader(new FileReader(file2));
			List<String[]> dataList = reader.readAll();
			dataList.remove(0);
			writer = new CSVWriter(new FileWriter(file1, true));
			writer.writeAll(dataList);
			reader.close();
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
}
