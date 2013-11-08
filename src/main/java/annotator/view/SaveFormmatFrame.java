package annotator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author Brandon B. Lin
 * 
 * Frame to choose which format save as
 *
 */
public class SaveFormmatFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ButtonPanel parent;
	private JCheckBox csv;
	private JCheckBox xml;
	private JButton continueButton;

	/**
	 * constructor
	 * @param superPanel
	 */
	public SaveFormmatFrame(ButtonPanel superPanel) {
		this.parent = superPanel;
		setTitle("Formmat to save");
		setLocation(200, 200);
		setSize(250, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponent();
	}
	
	
	/**
	 * init
	 */
	private void initComponent() {
		JPanel check = new JPanel();
		csv = new JCheckBox("csv");
		check.add(csv);
		
		xml = new JCheckBox("xml");
		check.add(xml);
		
		continueButton = new JButton("Continue");
		continueButton.addActionListener(this);
		check.add(continueButton);
		add(check);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		boolean csvState = csv.isSelected();
		boolean xmlState = xml.isSelected();
		
		if (csvState == false & xmlState == false) {
			// remind that
		} else if (csvState == true & xmlState == false){
			parent.setSaveType(0);
		} else if (csvState == false & xmlState == true) {
			parent.setSaveType(1);
		} else {
			parent.setSaveType(2);
		}
		
		this.setVisible(false);
		parent.saveResult();
		
	}

}
