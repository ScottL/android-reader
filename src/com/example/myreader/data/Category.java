package com.example.myreader.data;

import android.content.Context;

import com.example.myreader.R;

public class Category {


	private Context context;
	
	public Category(Context context){
		this.context = context;
	}
	
	public String getTopStories(){
		return context.getString(R.string.TopStories);
	}
	
	
	
}
