package annotator.controller;

import java.util.ArrayList;

import annotator.model.main.ImageModel;

/**
 * @author Brandon B. Lin
 * 
 */
public class CurrentImages {

	private static CurrentImages instance = null;
	private int current = 0;
	private static int total = 0;
	private ArrayList<ImageModel> imageModels = new ArrayList<>();

	public ImageModel getImageModelByIndex(int index) {
		return imageModels.get(index);
	}

	private CurrentImages(ArrayList<String[]> metadata) {
		total = metadata.size();
		// String flag = metadata.get(0)[0]; header has been removed
		int column = metadata.get(0).length;
		//System.out.println("Test in CurrentImages" + column);
		if (column != 10) {
			for (String[] image : metadata) {
				ImageModel i = new ImageModel();
				i.setTitle(image[3]);
				i.setFigureFileName(image[0]);
				i.setLegend(image[4]);
				imageModels.add(i);

			}
		} else {
			for (String[] media : metadata) {
				ImageModel i = new ImageModel();
				i.setType(media[0]); // media type
				i.setTitle(media[1]); // media title
				i.setFigureFileName(media[9]); // url
				i.setLegend(media[2]); // legend
				imageModels.add(i);
			}
		}

	}

	public int getTotal() {
		return total;
	}

	public void clear() {
		imageModels.removeAll(imageModels);
		total = 0;
	}

	public static CurrentImages getInstance(ArrayList<String[]> metadata) {
		if (instance == null || total==0) {
			instance = new CurrentImages(metadata);
		}
		return instance;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

}
