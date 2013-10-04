package com.example.myreader.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Article implements Parcelable  {
	
	private String title;
	private String description;
	private String url;
	private String encodedContent;
	private String pubDate;
	
	private long dbId;
	private String guid;
	private boolean read;
	private boolean hidden;
	
	
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
	
	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}
	
	public void setRead(boolean bool){
		this.read = bool;
	}
	
	public boolean getRead(){
		return read;
	}
	
	public void setHidden(boolean bool){
		this.hidden = bool;
	}
	
	public boolean getHidden(){
		return hidden;
	} 

	
	
	
	
	
	public Article(Parcel in){
		this.title = in.readString();
		this.description = in.readString();
		this.url = in.readString();
		this.encodedContent = in.readString();
		this.pubDate = in.readString();
		this.read = in.readByte() == 1;
		this.hidden = in.readByte() == 1;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(this.title);
		dest.writeString(this.description);
		dest.writeString(this.url);
		dest.writeString(this.encodedContent);
		dest.writeString(this.pubDate);	
		dest.writeByte((byte) (this.read ? 1 : 0)); 
		dest.writeByte((byte) (this.hidden ? 1: 0));
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
