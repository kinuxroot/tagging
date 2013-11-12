package annotator.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import annotator.controller.CurrentImages;
import annotator.controller.Options;
import annotator.model.ModelLocator;
import annotator.model.main.ImageModel;
import annotator.model.main.TermModel;
import annotator.util.ImageHelper;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Brandon B. Lin
 * 
 */
public class ButtonPanel extends JPanel implements ActionListener {

	/**
	 * Panel containing action buttons
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fc;
	private String defaultName;
	/* 0: csv 1: xml 2: both */
	private int saveType;

	/**
	 * constructor
	 * 
	 * @param parent
	 */
	public ButtonPanel(MainWindow parent) {
		setPreferredSize(new Dimension(1000, 50));
		setBorder(new TitledBorder("Operations"));
		setLayout(new GridLayout(1, 5, 15, 5));

		fc = new JFileChooser(new File("."));

		addButton("Previous", this);
		addButton("Next", this);
		add(new JLabel()); // can't be deleted
		addButton("Save", this);
		addButton("Exit", this);

	}

	/**
	 * get FileName to save
	 */
	private String getSaveFileName() {
		CurrentImages ci = CurrentImages.getInstance(null);
		int index = ci.getCurrent();
		ImageModel image = ci.getImageModelByIndex(index);
		String figureFileName = image.getFigureFileName();
		return figureFileName.substring(0, figureFileName.length()-4);

	}

	/**
	 * add button to panel with listener
	 * 
	 * @param text
	 * @param listener
	 */
	private void addButton(String text, ActionListener listener) {
		JButton button = new JButton(text);
		button.addActionListener(listener);
		add(button);
	}

	/**
	 * listener action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		switch (source) {
		case "Previous":
			previousAction();
			break;

		case "Next":
			nextAction();
			break;

		case "Save":
			saveAction();
			break;

		case "Exit":
			exitAction();
			break;
		default:
			break;
		}

	}

	/**
	 * action for previous button
	 */
	private void previousAction() {
		CurrentImages ci = CurrentImages.getInstance(null);
		int c = ci.getCurrent() - 1;
		if (c < 0) {
			JOptionPane.showConfirmDialog(null, "It is the First one!",
					"Attention", JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE);
		} else {
			ModelLocator.getInstance().getMainWindow()
					.refresh(ci.getImageModelByIndex(c));
			ci.setCurrent(c);
		}

	}

	/**
	 * action of next button
	 */
	private void nextAction() {
		CurrentImages ci = CurrentImages.getInstance(null);
		ImageModel currentImageModel = ci.getImageModelByIndex(ci.getCurrent());
		int c = ci.getCurrent() + 1;
		if (currentImageModel.isSaveState() == false && currentImageModel.getTags().size()>0) {
			int state = JOptionPane.showConfirmDialog(null,
					"You haven't save the file ,sure to move?", "Attention",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

			if (state == JOptionPane.YES_OPTION) {
				// ----------------------------------------------------
				if (c >= ci.getTotal()) {
					JOptionPane.showConfirmDialog(null, "It is the last one!",
							"Attention", JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE);
				} else {
					ModelLocator.getInstance().getMainWindow()
							.refresh(ci.getImageModelByIndex(c));
					ci.setCurrent(c);
				}
				// ----------------------------------------------------
			}
		} else {
			// ----------------------------------------------------------
			if (c >= ci.getTotal()) {
				JOptionPane.showConfirmDialog(null, "It is the last one!",
						"Attention", JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE);
			} else {
				ModelLocator.getInstance().getMainWindow()
						.refresh(ci.getImageModelByIndex(c));
				ci.setCurrent(c);
			}
			// --------------------------------------------------------------

		}

	}

	/**
	 * action of save button
	 */
	private void saveAction() {
		CurrentImages ci = null;
		ImageModel currentImageModel = null;
		try {
			ci = CurrentImages.getInstance(null);
			currentImageModel = ci.getImageModelByIndex(ci.getCurrent());
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Load metadata First!", "Load metadata",
					JOptionPane.CLOSED_OPTION);
		}
		
		if(currentImageModel== null) {
			// do nothing
		} else if (currentImageModel.getTags().size() == 0) {
			JOptionPane.showConfirmDialog(null, "No tag has been added!", "No tags",
					JOptionPane.CLOSED_OPTION);
		} else {
			SaveFormmatFrame frame = new SaveFormmatFrame(this);
			frame.pack();
			frame.setVisible(true);
		}
		
	}

	public void saveResult() {
		if (saveType == 0) {
			saveAsCsv();
		} else if (saveType == 1) {
			saveAsXml();
		} else {
			saveAsBoth();
		}
	}

	/**
	 * save the annotation as csv
	 */
	private void saveAsCsv() {
		CSVWriter writer;
		ImageModel currentImageModel = ImageHelper.getCurrentImageModel();
		ArrayList<TermModel> tags = currentImageModel.getTags();

		fc = new JFileChooser(new File("."));
		fc.setDialogTitle("Save the resulr as csv");
		fc.setMultiSelectionEnabled(false);
		defaultName = getSaveFileName();
		fc.setSelectedFile(new File(defaultName + ".csv"));
		int state = fc.showSaveDialog(null);

		if (state == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				writer = new CSVWriter(new FileWriter(file));
				List<String[]> data = new ArrayList<String[]>();
				data.add(new String[] { "TermOfInterest", "Axis",
						"MatchedTerm", "FullId", "MatchType", "ontology" });
				for (TermModel tag : tags) {
					String termName = tag.getName();
					String termFromLegend = tag.getSeachTerm();
					String ontology = tag.getOntology().getVirtualId();
					String fullId = tag.getFullId().toString();
					String matchType = tag.getLevel();
					String axis = tag.getAxis().getName();
					String[] temp = { termFromLegend, axis, termName, fullId,
							matchType, ontology };
					data.add(temp);
				}
				writer.writeAll(data);
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			currentImageModel.setSaveState(true);
			JOptionPane.showConfirmDialog(null, "saved successfully", "Save",
					JOptionPane.CLOSED_OPTION);
		}

	}

	/**
	 * save results as xml
	 */
	private void saveAsXml() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		ImageModel currentImageModel = ImageHelper.getCurrentImageModel();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();

			ArrayList<TermModel> tags = currentImageModel.getTags();

			Element rootElement = doc.createElement("record");
			doc.appendChild(rootElement);

			for (TermModel tag : tags) {
				String termName = tag.getName();
				String termFromLegend = tag.getSeachTerm();
				String ontology = tag.getOntology().getVirtualId();
				String fullId = tag.getFullId().toString();
				String matchType = tag.getLevel();
				String axis = tag.getAxis().getName();

				// staff elements
				Element term = doc.createElement("term");
				rootElement.appendChild(term);

				// set attribute to staff element
				Attr attr = doc.createAttribute("id");
				attr.setValue("1");
				term.setAttributeNode(attr);

				// shorten way
				// staff.setAttribute("id", "1");

				// term of interest
				Element termOfInterest = doc.createElement("TermOfInterest");
				termOfInterest.appendChild(doc.createTextNode(termFromLegend));
				term.appendChild(termOfInterest);

				// axis
				Element anAxis = doc.createElement("Axis");
				anAxis.appendChild(doc.createTextNode(axis));
				term.appendChild(anAxis);

				// matched term
				Element anMatchedTerm = doc.createElement("MatchedTerm");
				anMatchedTerm.appendChild(doc.createTextNode(termName));
				term.appendChild(anMatchedTerm);

				// full id
				Element anFullId = doc.createElement("FullId");
				anFullId.appendChild(doc.createTextNode(fullId));
				term.appendChild(anFullId);

				// match type
				Element anMatchType = doc.createElement("MatchType");
				anMatchType.appendChild(doc.createTextNode(matchType));
				term.appendChild(anMatchType);

				// ontology
				Element anOntology = doc.createElement("Ontology");
				anOntology.appendChild(doc.createTextNode(ontology));
				term.appendChild(anOntology);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			String xmlSavePath = Options.savePath4Annotation + File.separator
					+ currentImageModel.getFigureFileName() + ".xml";
			StreamResult result = new StreamResult(new File(xmlSavePath));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.setOutputProperty("encoding", "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");

			transformer.transform(source, result);
		} catch (TransformerException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		currentImageModel.setSaveState(true);
		System.out.println("File saved!");

	}

	private void saveAsBoth() {
		saveAsCsv();
		saveAsXml();
	}

	/**
	 * action of exit button
	 */
	private void exitAction() {
		System.exit(0);
	}

	/**
	 * @return the saveType
	 */
	public int getSaveType() {
		return saveType;
	}

	/**
	 * @param saveType
	 *            the saveType to set
	 */
	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

}
