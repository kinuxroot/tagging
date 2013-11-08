package annotator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RightPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResultPanel resultPanel;
	private ResultPanel recommendedPanel;

	public RightPanel() {

		setPreferredSize(new Dimension(300, 350));
		BorderLayout layout = new BorderLayout();
		layout.setHgap(5);
		layout.setVgap(10);
		setLayout(layout);

		initComponent();

	}

	private void initComponent() {
		resultPanel = new ResultPanel("Search Results");
		resultPanel.setPreferredSize(new Dimension(270, 600));
		JPanel p = new JPanel();
		p.add(resultPanel);
		JScrollPane pane1 = new JScrollPane(p);
		pane1.setPreferredSize(new Dimension(330, 175));
		add(pane1, BorderLayout.NORTH);

		recommendedPanel = new ResultPanel("Recommended Result");
		recommendedPanel.setPreferredSize(new Dimension(270, 600));
		JPanel p1 = new JPanel();
		p1.add(recommendedPanel);
		JScrollPane pane = new JScrollPane(p1);
		add(pane, BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	public void clear() {		
		clearSearchResult();
		clearRecommendedResult();
	}
	
	public void clearSearchResult() {
		Component[] components = resultPanel.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(false);
		}
	}
	
	public void clearRecommendedResult() {
		Component[] components = recommendedPanel.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(false);
		}
	}
	
	public ResultPanel getResultPanel() {
		return resultPanel;
	}

	public ResultPanel getRecommendedPanel() {
		return recommendedPanel;
	}

}
