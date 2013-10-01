package com.example.myreader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class CategoryHandler {
	
	private static List<String> mGroupName;
    private static HashMap<String, List<String>> mChildData;

    
	public static void prepareListData() {
			
		if (mGroupName != null && mChildData != null){
			Log.e("CategoryHandler", "Not Null");
			return;
		}
		
		Log.e("CategoryHandler", "Null");
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
	
	public static List<String> getGroupData(){
		return mGroupName;
	}
	
	public static HashMap<String, List<String>> getChildData(){
		return mChildData;
	}
	
	
}
