package com.example.myreader.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myreader.DetailActivity;
import com.example.myreader.data.Article;


public class HtmlService extends AsyncTask<String, Void, String>{

	
	private DetailActivity detailActivity;
	private Article currArticle;
	
	public HtmlService(DetailActivity detailActivity, Article article) {
		Log.e("HtmlService", "PRE EXECUTE");
		this.detailActivity = detailActivity;
		this.currArticle = article;
	}
	
	protected void onPreExecute() {
		Log.e("HtmlService", "PRE EXECUTE");
		detailActivity.ShowProgress.show();
	}
	
	protected void onPostExecute(String scrapedContent) {
		Log.e("HtmlService", "POST EXECUTE");
		detailActivity.setArticleContent(scrapedContent);
		detailActivity.ShowProgress.dismiss();
	}
	
	
	
	@Override
	protected String doInBackground(String... urls) {
		/*
		HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(urls[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }*/
				
		String feed = urls[0];
		URL url = null;
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		
		
		try{
			url = new URL(feed);
			reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			for (String line; (line = reader.readLine()) != null;) {
	            builder.append(line.trim());
	        }
		} catch (IOException e) {
			Log.e("RSS Handler IO", e.getMessage() + " >> " + e.toString());
		} finally {
			if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
	    }
		
		//Yahoo rss
		//String tag = "<div class=\"yom-mod yom-art-content \" id=\"mediaarticlebody\"><div class=\"bd\"><p class=\"first\">";
		//String start = tag;
	    //String end = "</div>";
	    
	    //NYT rss
	    //String tag = "<h6 class=\"dateline\">";
		//String start = tag;
	    //String end = "</h6>";
	    
	    //TechCrunch
	    String tag = "<div class=\"body-copy\">";
		String start = tag;
	    String end = "</div>";
	    
	    //sLog.e("HtmlService", "" + builder.indexOf(start));
	    
	    String part = builder.substring(builder.indexOf(start) + start.length());
	
	    String question = part.substring(0, part.indexOf(end));
	    //question = question.replaceAll("</p>", "</p>TTTTT");
	    //question = question.replaceAll("<(.|\n)*?>", "");
	   // question = question.replaceAll("TTTTT", "\n\n"); 
	    
		return question;
	}


}