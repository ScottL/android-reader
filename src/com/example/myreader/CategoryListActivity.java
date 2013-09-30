package com.example.myreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.myreader.data.Category;
import com.example.myreader.data.Publisher;
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
        
        prepareListData();
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
	
	
	private void prepareListData() {
		
		mGroupName = new ArrayList<String>();
		mChildData = new HashMap<String, List<String>>();
 
        for (int i = 0; i < Category.CategoryList.length; i++){
        	mGroupName.add(Category.CategoryList[i]);
        }
 

        List<String> Technology = new ArrayList<String>();
        Technology.add(Publisher.TechCrunch);
        Technology.add(Publisher.Engadget);
        Technology.add(Publisher.CNET);
        
        List<String> USNews = new ArrayList<String>();
        USNews.add(Publisher.Yahoo);
        USNews.add(Publisher.CNN);
        
        List<String> WorldNews = new ArrayList<String>();
        WorldNews.add(Publisher.Google);
        WorldNews.add(Publisher.NYT);
        
        List<String> Empty = new ArrayList<String>(0);
 
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.TopStories)), Empty);
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.Technology)), Technology);
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.USNews)), USNews);
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.WorldNews)), WorldNews);
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.Business)), Empty);
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.Sports)), Empty);
        mChildData.put(mGroupName.get(mGroupName.indexOf(Category.Entertainment)), Empty);
    }

}
