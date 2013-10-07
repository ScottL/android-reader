package com.example.myreader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myreader.R;
import com.example.myreader.data.Article;


public class EfficientAdapter extends ArrayAdapter<Article> {
	
	 //HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
	  private final Context mContext;
	  private final List<Article> mArticles;

	public EfficientAdapter(Context context, int textViewResourceId,
			List<Article> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		this.mArticles = objects;
	}
	
	/*
	public long getItemId(int position) {
	      String item = getItem(position).getTitle();
	      return mIdMap.get(item);
    }
	
	public boolean hasStableIds() {
	      return true;
    }*/
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.article_row, parent, false);
	    
	    //Title
	    TextView titleText = (TextView) rowView.findViewById(R.id.article_title);
	    titleText.setText(mArticles.get(position).getTitle());    
   
	    //Date
	    SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
 	   	Date date;
		try {
			date = df.parse(mArticles.get(position).getPubDate());
			TextView dateText = (TextView) rowView.findViewById(R.id.article_date);
		 	dateText.setText(DateUtil.getDateDifference(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		//Publisher
		TextView publisherText = (TextView) rowView.findViewById(R.id.article_publisher);
		publisherText.setText(mArticles.get(position).getPublisher());
		
		
		if((mArticles.get(position).getRead())){
	    	rowView.setBackgroundColor(Color.parseColor("#A0000011"));
	    }
		
		if(mArticles.get(position).getHidden()){
			titleText.setText("---HIDDEN---");
		}
	    
		return rowView;
	} 
	
	
}
