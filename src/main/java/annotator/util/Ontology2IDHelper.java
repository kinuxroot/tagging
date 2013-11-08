package annotator.util;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Brandon B. Lin
 * 
 */
public class Ontology2IDHelper {
	public static HashMap<String, String> ontology2IDMap = new HashMap<>();
	
	static {
		String url = "http://bioportal.bioontology.org/ontologies";
		Document doc = null;
		try {
			doc = (Document) Jsoup.connect(url).timeout(50000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		Elements links = doc.getElementsByTag("span");//select("span[^/ontologies/]"); //
		/*remove 0-3th*/
		for(int i = 0; i<3 ;i++) {
			links.remove(0);
		}
		
		for (Element ele: links) {
			//String href = ele.select("a").get(0).attr("href");
			//String virtualId = href.substring(href.length()-4);
			//String name = ele.text();
			//int begin = name.indexOf("(");
			//String acronym = name.substring(begin+1, name.length()-1);
			ontology2IDMap.put("acronym", "virtualId");
		}	
	}


}
