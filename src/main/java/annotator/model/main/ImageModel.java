package annotator.model.main;

import java.util.ArrayList;

/**
 * @author Brandon B. Lin
 * 
 */
public class ImageModel  {

	/* title,file name, legend of the figure */
	private String type = "picture";
	private String title = "Title of Figure";
	private String figureFileName;
	private String legend = "Here is the legend";
	private ArrayList<TermModel> tags = new ArrayList<>();
	private boolean saveState = false;

	public ArrayList<TermModel> getTags() {
		return tags;
	}

	public void setTags(ArrayList<TermModel> tags) {
		this.tags = tags;
	}
	
	public void addTag(TermModel term) {
		this.tags.add(term);
	}
	
	public void removeTag(TermModel term) {
		tags.remove(term);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFigureFileName() {
		return figureFileName;
	}

	public void setFigureFileName(String figureFileName) {
		this.figureFileName = figureFileName;
		
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the saveState
	 */
	public boolean isSaveState() {
		return saveState;
	}

	/**
	 * @param saveState the saveState to set
	 */
	public void setSaveState(boolean saveState) {
		this.saveState = saveState;
	}

}
