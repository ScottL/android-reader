package com.example.myreader.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Article implements Parcelable  {
	
	private String guid;
	private String title;
	private String description;
	private String url;
	private String encodedContent;
	private String pubDate;
	private boolean read;
	private long dbId;
	
	
	public Article(){
		
	}
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public void setEncodedContent(String encodedContent) {
		this.encodedContent = extractCData(encodedContent);
	}

	public String getEncodedContent() {
		return encodedContent;
	}
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getPubDate() {
		return pubDate;
	}
	
	private String extractCData(String data){
		data = data.replaceAll("<!\\[CDATA\\[", "");
		data = data.replaceAll("\\]\\]>", "");
		
		data = data.replaceAll("<(.|\n)*?>", "");
		data = data.replaceAll("\n", "\n\n");
		
		
		return data;
	}
	
	public void setRead(boolean bool){
		this.read = bool;
	}
	
	public boolean getRead(){
		return read;
	}
	
	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}


	
	
	
	
	
	public Article(Parcel in){
		String[] data= new String[5];
		 
		in.readStringArray(data);
		this.title= data[0];
		this.description= data[1];
		this.url= data[2];
		this.encodedContent = data[3];
		this.pubDate = data[4];
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeStringArray(new String[]{this.title,this.description,this.url,this.encodedContent,this.pubDate});
	}

	public static final Parcelable.Creator<Article> CREATOR= new Parcelable.Creator<Article>() {
		 
		@Override
		public Article createFromParcel(Parcel source) {
			return new Article(source);
		}
		 
		@Override
		public Article[] newArray(int size) {
			return new Article[size];
		}
	};
}
