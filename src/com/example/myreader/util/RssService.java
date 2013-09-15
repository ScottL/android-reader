package com.example.myreader.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myreader.Menu;
import com.example.myreader.data.Article;


public class RssService extends AsyncTask<String, Void, List<Article>>{

	private Menu menu;
	
	public RssService(Menu menu) {
		this.menu = menu;

	}
	
	protected void onPreExecute() {
		Log.e("RssService", "PRE EXECUTE");
		menu.ShowProgress.show();
	}
	
	protected void onPostExecute(List<Article> articles) {
		Log.e("RssService", "POST EXECUTE");
		menu.setRSSResult(articles);
		menu.ShowProgress.dismiss();
	}
	
	
	@Override
	protected List<Article> doInBackground(String... urls) {
		
		String feed = urls[0];
		URL url = null;
		try {

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL(feed);
			RssHandler rh = new RssHandler();
			xr.setContentHandler(rh);
			
			for(int i = 0; i < urls.length; i++){
				url = new URL(urls[i]);
				xr.parse(new InputSource(url.openStream()));
			}
			
			//xr.parse(new InputSource(url.openStream()));

			Log.e("RssService", "PARSING FINISHED");
			Log.e("RssService", "Article List Size: " + rh.getArticleList().size());	
			

			return rh.getArticleList();

		} catch (IOException e) {
			Log.e("RSS Handler IO", e.getMessage() + " >> " + e.toString());
		} catch (SAXException e) {
			Log.e("RSS Handler SAX", e.toString());
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Log.e("RSS Handler Parser Config", e.toString());
		}
		return null;
	}

}
