package annotator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import tagger.views.WelcomeFrame;

import annotator.model.main.ImageModel;

/**
 * @author Brandon B. Lin
 * 
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 7429930823616326561L;
	/* menu bar */
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu helpMenu;
	private JMenu saveAsMenu;

	/* panels */
	private TopPanel top;
	private TagPanel tagPanel;
	private ButtonPanel buttonPanel;

	/* file chooser */
	private JFileChooser fc;

	private WelcomeFrame parent;

	// private ImageModel defaultImageModel;

	public MainWindow(WelcomeFrame parent, ImageModel defaultImageModel) {
		this.parent = parent;
		fc = new JFileChooser(new File("."));
		fc.setDialogTitle("import existing annotation file");
		fc.setFileFilter(new FileNameExtensionFilter("csv files", "csv"));
		fc.setAcceptAllFileFilterUsed(false);

		String logo = "resource" + File.separator + "images" + File.separator
				+ "logo.png";
		ImageIcon img = new ImageIcon(logo);
		this.setIconImage(img.getImage());
		// this.defaultImageModel = defaultImageModel;
		initComponents();
		addListeners();

	}

	/**
	 * init the main frame
	 */
	private void initComponents() {

		menuBar = createMenuBar();
		setJMenuBar(menuBar);

		top = new TopPanel(this);
		add(top, BorderLayout.NORTH);

		tagPanel = new TagPanel(this);

		JScrollPane p = new JScrollPane(tagPanel);
		add(p, BorderLayout.CENTER);

		buttonPanel = new ButtonPanel(this);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * create Menubar
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();

		// ------------File Menu ---------
		fileMenu = new JMenu("File");
		/* load image and metadata */
		JMenuItem loadMenuItem = new JMenuItem("Load metadata & images");
		loadMenuItem.setMnemonic(KeyEvent.VK_L);
		fileMenu.add(loadMenuItem);

		/* import existing annotation */
		JMenuItem importMenuItem = new JMenuItem(
				"Import existing annotation file");
		importMenuItem.setMnemonic(KeyEvent.VK_I);
		fileMenu.add(importMenuItem);

		/* load multimedia file */
		JMenuItem multiFileMenuItem = new JMenuItem(
				"Load metadata for multimedia files");
		importMenuItem.setMnemonic(KeyEvent.VK_N);
		fileMenu.add(multiFileMenuItem);

		/* save as */
		saveAsMenu = new JMenu("save as ...");
		JMenuItem saveAsCsv = new JMenuItem("save as csv");
		JMenuItem saveAsXml = new JMenuItem("save as xml");
		JMenuItem saveAsBoth = new JMenuItem("save as csv & xml");
		saveAsMenu.add(saveAsCsv);
		saveAsMenu.add(saveAsXml);
		saveAsMenu.add(saveAsBoth);

		saveAsMenu.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(saveAsMenu);

		bar.add(fileMenu);

		// ---------------Edit Menu----------
		editMenu = new JMenu("Edit");
		JMenuItem undoItem = new JMenuItem("undo");
		undoItem.setMnemonic(KeyEvent.VK_U);
		editMenu.add(undoItem);
		JMenuItem redoItem = new JMenuItem("redo");
		redoItem.setMnemonic(KeyEvent.VK_R);
		editMenu.add(redoItem);
		bar.add(editMenu);

		// ---------------Vuew Menu----------
		viewMenu = new JMenu("View");
		JMenuItem viewSetting = new JMenuItem("view settings");
		viewMenu.add(viewSetting);
		bar.add(viewMenu);

		// ---------------Help Menu----------
		helpMenu = new JMenu("Help");
		JMenuItem howItem = new JMenuItem("How to use");
		helpMenu.add(howItem);
		bar.add(helpMenu);

		return bar;
	}

	/**
	 * add listeners
	 */
	private void addListeners() {
		JMenuItem item = menuBar.getMenu(0).getItem(0);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoadFrame loadFrame = new LoadFrame();
				loadFrame.setVisible(true);

			}
		});

		item = menuBar.getMenu(0).getItem(1);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int state = fc.showOpenDialog(null);
				if (state == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();
					ImportFrame frame = new ImportFrame(file);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}

			}
		});

		/*
		 * load multi media file
		 */
		item = menuBar.getMenu(0).getItem(2);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoadMultiFileFrame frame = new LoadMultiFileFrame();
				frame.setVisible(true);

			}
		});

		/*
		 * view settings
		 */
		item = menuBar.getMenu(2).getItem(0);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SettingFrame frame = new SettingFrame(
						parent.getAxes2Ontology(), parent.getCheckPanel()
								.getBioPortalCheckBox().isSelected());
				frame.setVisible(true);

			}
		});
	}

	/**
	 * refresh the frame
	 * 
	 * @param image
	 */
	public void refresh(ImageModel image) {
		top.refresh(image);
		tagPanel.clear();
	}

	/**
	 * @return
	 */
	public TagPanel getTagPanel() {
		return tagPanel;
	}

}
