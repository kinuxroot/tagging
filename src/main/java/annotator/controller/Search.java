package annotator.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Search {

	private static final String annotatorUrl = "http://rest.bioontology.org/obs/annotator";
	private static final String apiKey = "1b466c88-c956-4682-8319-5e01f905b4f4";

	/**
	 * 
	 * @param term
	 *            : search term
	 * @param ontologies
	 *            : ontologies to search within
	 * @param saveOrNot
	 *            : whether save the result as xml
	 * @param fileName
	 *            : fileName to save (should include extension)
	 * @return the completed path to save
	 */
	public static File searchByTerm(String term, String ontologies,
			String filePath) {
		File file = new File(filePath); // don't create

		try {

			HttpPost httpPost = new HttpPost(annotatorUrl);
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			// set the params
			params.add(new BasicNameValuePair("start", "0"));
			params.add(new BasicNameValuePair("size", "14"));
			params.add(new BasicNameValuePair("sortType", "hot"));
			// params.add(new BasicNameValuePair("longestOnly", "true"));
			params.add(new BasicNameValuePair("wholeWordOnly", "true"));
			params.add(new BasicNameValuePair("filterNumber", "true"));
			params.add(new BasicNameValuePair(
					"stopWords",
					"I,a,above,after,against,all,alone,always,am,amount,an,and,any,are,around,as,at,back,be,before,behind,below,between,bill,both,bottom,by,call,can,co,con,de,detail,do,done,down,due,during,each,eg,eight,eleven,empty,ever,every,few,fill,find,fire,first,five,for,former,four,from,front,full,further,get,give,go,had,has,hasnt,he,her,hers,him,his,i,ie,if,in,into,is,it,last,less,ltd,many,may,me,mill,mine,more,most,mostly,must,my,name,next,nine,no,none,nor,not,nothing,now,of,off,often,on,once,one,only,or,other,others,out,over,part,per,put,re,same,see,serious,several,she,show,side,since,six,so,some,sometimes,still,take,ten,then,third,this,thick,thin,three,through,to,together,top,toward,towards,twelve,two,un,under,until,up,upon,us,very,via,was,we,well,when,while,who,whole,will,with,within,without,you,yourself,yourselves"));
			params.add(new BasicNameValuePair("withDefaultStopWords", "true"));
			params.add(new BasicNameValuePair("isTopWordsCaseSensitive",
					"false"));
			params.add(new BasicNameValuePair("mintermSize", "3"));
			params.add(new BasicNameValuePair("scored", "true"));
			params.add(new BasicNameValuePair("withSynonyms", "true"));
			params.add(new BasicNameValuePair("ontologiesToExpand", ""));
			params.add(new BasicNameValuePair("ontologiesToKeepInResult",
					ontologies));
			params.add(new BasicNameValuePair("isVirtualOntologyId", "true"));
			params.add(new BasicNameValuePair("semanticTypes", ""));
			params.add(new BasicNameValuePair("levelMax", "1"));
			params.add(new BasicNameValuePair("mappingTypes", "null")); // null,Automatic,Manual
			params.add(new BasicNameValuePair("textToAnnotate", term));
			params.add(new BasicNameValuePair("format", "xml")); // 'text','xml',
			params.add(new BasicNameValuePair("apikey", apiKey));
			// coding
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// send request
			// DefaultHttpClient has been deprecated
			HttpResponse response = 
					HttpClientBuilder.create().build().execute(httpPost);
			// if success
			if (response.getStatusLine().getStatusCode() == 200) {

				/* search result as String */
				String result = EntityUtils.toString(response.getEntity());
				FileWriter fw = new FileWriter(file); // now the file is created
														// on file system
				fw.write(result); // write
				fw.flush();
				fw.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}

}
