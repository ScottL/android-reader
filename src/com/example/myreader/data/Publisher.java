package com.example.myreader.data;

public class Publisher {

	public final static String TechCrunch = "TechCrunch";	
	public final static String Engadget = "Engadget";	
	public final static String CNET = "CNET";	
	public final static String Yahoo = "Yahoo";	
	public final static String CNN = "CNN";	
	public final static String Google = "Google";	
	public final static String NYT = "New York Times";	
	public final static String TEMP = "Temp";
	
	
	public final static String TechCrunchL = "http://feeds.feedburner.com/TechCrunch";
	public final static String EngadgetL = "http://www.engadget.com/rss.xml";
	public final static String CNETL = "http://feeds.feedburner.com/cnet/NnTv";
	public final static String YahooL = "http://news.yahoo.com/rss/us";
	public final static String CNNL = "http://rss.cnn.com/rss/cnn_us.rss";
	public final static String GoogleL = "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&output=rss";
	public final static String NYTL = "http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
	
	
	public static String URLtoName(String publisherURL){
		if(publisherURL.equals(TechCrunchL)){
			return TechCrunch;
		}else if (publisherURL.equals(EngadgetL)){
			return Engadget;
		}else if (publisherURL.equals(CNETL)){
			return CNET;
		}else if (publisherURL.equals(YahooL)){
			return Yahoo;
		}else if (publisherURL.equals(CNNL)){
			return CNN;
		}else if (publisherURL.equals(GoogleL)){
			return Google;
		}else if (publisherURL.equals(NYTL)){
			return NYT;
		}
		return null;
	}
	
}
