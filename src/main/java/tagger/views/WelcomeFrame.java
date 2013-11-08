package tagger.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Brandon B. Lin
 * 
 */
public class WelcomeFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel headLabel;
	private AxesPanel axesPanel;
	private CheckPanel checkPanel;
	

	private HashMap<String, String> axes2Ontology = new HashMap<String, String>();

	public WelcomeFrame() {
		initComponent();
	}

	/**
	 * 
	 */
	private void initComponent() {
		setTitle("Image Tagger");
		/* set icon */
		
		String logo = "resource" + File.separator + "images" + File.separator
		 + "logo.png"; 
		ImageIcon img = new ImageIcon(logo);
		this.setIconImage(img.getImage());
		 
		setSize(700, 600);
		setLayout(new BorderLayout());// setPreferredSize(new Dimension(600,
										// 400));
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.YELLOW);

		addPanels();
		// System.out.println("hh3");
		setVisible(true);
		// System.out.println("hh2");

	}

	/**
	 * 
	 */
	private void addPanels() {
		headLabel = new JLabel("Welcome to Image Tagger!");
		headLabel.setHorizontalAlignment(JLabel.CENTER);
		headLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		add(headLabel, BorderLayout.NORTH);

		axesPanel = new AxesPanel(this);
		add(axesPanel, BorderLayout.CENTER);

		checkPanel = new CheckPanel(this);
		add(checkPanel, BorderLayout.SOUTH);

	}

	public void addAxes(String axes, String ontology) {
		axes2Ontology.put(axes, ontology);
	}

	public AxesPanel getAxesPanel() {
		return axesPanel;
	}

	public HashMap<String, String> getAxes2Ontology() {
		return axes2Ontology;
	}
	
	public CheckPanel getCheckPanel() {
		return checkPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
