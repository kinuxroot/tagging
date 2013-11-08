/**
 * 
 */
package annotator.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Brandon B. Lin
 * 
 */
public class LargeImageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	private JLabel label;
	private int size;

	public LargeImageFrame(ImageIcon icon, int size) {
		this.icon = icon;
		this.size = size;
		setSize(size, size+50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponent();
	}

	private void initComponent() {
		label = new JLabel();
		label.setPreferredSize(new Dimension(size, size+50));
		label.setIcon(icon);
		JPanel panel = new JPanel();
		panel.add(label);
		add(panel);
	}

}
