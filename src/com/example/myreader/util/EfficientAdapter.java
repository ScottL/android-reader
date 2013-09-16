package com.example.myreader.util;

import java.util.List;

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
	  private final Context context;
	  private final List<Article> articles;

	public EfficientAdapter(Context context, int textViewResourceId,
			List<Article> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.articles = objects;
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
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.article_row, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.title);
	    textView.setText(articles.get(position).getTitle());
	    if((articles.get(position).getRead())){
	    	textView.setBackgroundColor(Color.parseColor("#A0000011"));
	    }

		return rowView;
	} 
	
	
}
