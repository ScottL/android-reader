package com.example.myreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.myreader.util.ExpandableCategoryAdaptor;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class CategoryListActivity extends ExpandableListActivity{

	public final static String CATEGORY_NAME = "com.example.MyReader.CATEGORY_NAME";
	public final static String[] CATEGORY_LIST = {"Top Stories", "Technology", "Business", "World News", "U.S. News", "Sports", "Entertainment"};
	
	ExpandableCategoryAdaptor mCategoryListAdapter;
    List<String> mGroupName;
    HashMap<String, List<String>> mChildData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		getActionBar().hide();
		
		//CategoryAdaptor categoryListAdaptor = new CategoryAdaptor(this, android.R.layout.simple_list_item_1, CATEGORY_LIST);
        //setListAdapter(categoryListAdaptor);  
        
        prepareListData();
        mCategoryListAdapter = new ExpandableCategoryAdaptor(this, mGroupName, mChildData);
        setListAdapter(mCategoryListAdapter);
	}
		
	
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		super.onChildClick(parent, v, groupPosition, childPosition, id);
		
		Intent intent = new Intent(this, Menu.class);
		intent.putExtra(CATEGORY_NAME, mGroupName.get(groupPosition));
		startActivity(intent);    
		
		return false;
	}
	
	
	
	private void prepareListData() {
		mGroupName = new ArrayList<String>();
		mChildData = new HashMap<String, List<String>>();
 
        for (int i = 0; i < CATEGORY_LIST.length; i++){
        	mGroupName.add(CATEGORY_LIST[i]);
        }
 
        List<String> TopStories = new ArrayList<String>();
        TopStories.add("Yahoo");
        TopStories.add("TechCrunch");
        TopStories.add("Google");
        
        List<String> Empty = new ArrayList<String>(0);
 

        mChildData.put(mGroupName.get(0), TopStories);
        mChildData.put(mGroupName.get(1), Empty);
        mChildData.put(mGroupName.get(2), Empty);
        mChildData.put(mGroupName.get(3), Empty);
        mChildData.put(mGroupName.get(4), Empty);
        mChildData.put(mGroupName.get(5), Empty);
        mChildData.put(mGroupName.get(6), Empty);
    }

}
