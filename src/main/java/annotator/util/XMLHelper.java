/**
 * 
 */
package annotator.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Brandon B. Lin
 *
 */
public class XMLHelper {
	
	public static List<Element> getChildrenByTagName(Element parent, String name) {
	    List<Element> nodeList = new ArrayList<Element>();
	    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	      if (child.getNodeType() == Node.ELEMENT_NODE && 
	          name.equals(child.getNodeName())) {
	        nodeList.add((Element) child);
	      }
	    }

	    return nodeList;
	  }

}
