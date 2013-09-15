package com.example.myreader;

import com.example.myreader.util.CategoryAdaptor;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class CategoryListActivity extends ListActivity{

	public final static String CATEGORY_NAME = "com.example.MyReader.CATEGORY_NAME";
	public final static String[] CATEGORY_LIST = {"Top Stories", "Technology", "Business", "World News", "U.S. News", "Sports", "Entertainment"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		getActionBar().hide();
		
		CategoryAdaptor categoryListAdaptor = new CategoryAdaptor(this, android.R.layout.simple_list_item_1, CATEGORY_LIST);
        setListAdapter(categoryListAdaptor);  
	}
		
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, Menu.class);
		intent.putExtra(CategoryListActivity.CATEGORY_NAME, CATEGORY_LIST[position]);
		startActivity(intent);      
	}

}
