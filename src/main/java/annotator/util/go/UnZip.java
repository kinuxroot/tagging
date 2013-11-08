package annotator.util.go;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * @author Brandon B. Lin
 * 
 */
public class UnZip {

	 /**
	 * @param input_gzip_file
	 * @param outputFile 
	 */
	public static void gunzipIt(String input_gzip_file, String outputFile ){
		 
	     byte[] buffer = new byte[1024];
	 
	     try{
	 
	    	 GZIPInputStream gzis = 
	    		new GZIPInputStream(new FileInputStream(input_gzip_file));
	 
	    	 FileOutputStream out = 
	            new FileOutputStream(outputFile);
	 
	        int len;
	        while ((len = gzis.read(buffer)) > 0) {
	        	out.write(buffer, 0, len);
	        }
	 
	        gzis.close();
	    	out.close();
	 
	    	//System.out.println("Done");
	 
	    }catch(IOException ex){
	       ex.printStackTrace();   
	    }
	   } 
	
	public static void main(String[] args) {
		//String zipFile = "D:\\drop-box\\Dropbox\\Ontology Files\\GO Annotation Files\\Gallus_gallus.gz";
		/*String inputFile = "D:\\drop-box\\Dropbox\\Ontology Files\\GO Annotation Files\\Gallus_gallus";
	    String outputFolder = "D:\\drop-box\\Dropbox\\Ontology Files\\GO Annotation Files";
	    Trans2CSV.transfer2Csv(new File(inputFile), outputFolder);*/
		/*String s = "ex.gz";
		System.out.println(s.substring(0, s.length()-3));
		*/

	}
		
}
