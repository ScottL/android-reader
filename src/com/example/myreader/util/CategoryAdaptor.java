package com.example.myreader.util;

import com.example.myreader.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class CategoryAdaptor extends ArrayAdapter<String>  {

	private final Context context;
	private final String[] names;
	final static String[] COLORS = {"#33B5E5", "#AA66CC", "#99CC00", "#FFBB33", "#FF4444", "#0099CC", "#9933CC", "#669900", "#FF8800" , "#CC0000"};
	
	public CategoryAdaptor(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.names = objects;
	}

	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.category_row, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.category_name);
	    textView.setText(names[position]);
	    textView.setTextColor(Color.parseColor(COLORS[position % 10]));
	    textView.getBackground().setAlpha(190);
		
		return rowView;
	} 

}
