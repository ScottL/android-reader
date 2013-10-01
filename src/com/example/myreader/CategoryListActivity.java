package com.example.myreader;

import java.util.HashMap;
import java.util.List;

import com.example.myreader.data.CategoryHandler;
import com.example.myreader.util.ExpandableCategoryAdaptor;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class CategoryListActivity extends ExpandableListActivity{

	public final static String CATEGORY_NAME = "com.example.MyReader.CATEGORY_NAME";
	public final static String PUBLISHER_NAME = "com.example.MyReader.PUBLISHER_NAME";
	
	ExpandableCategoryAdaptor mCategoryListAdapter;
    List<String> mGroupName;
    HashMap<String, List<String>> mChildData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		getActionBar().hide();
        
		CategoryHandler.prepareListData();
		mGroupName = CategoryHandler.getGroupData();
		mChildData = CategoryHandler.getChildData();
        mCategoryListAdapter = new ExpandableCategoryAdaptor(this, mGroupName, mChildData);
        setListAdapter(mCategoryListAdapter);
	}
		
	
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		super.onChildClick(parent, v, groupPosition, childPosition, id);
		
		Intent intent = new Intent(this, Menu.class);
		intent.putExtra(CATEGORY_NAME, mGroupName.get(groupPosition));
		intent.putExtra(PUBLISHER_NAME, mChildData.get(mGroupName.get(groupPosition)).get(childPosition));
		startActivity(intent);    
		
		return false;
	}

}
