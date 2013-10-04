package com.example.myreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.database.ArticleDbAdaptor;
import com.example.myreader.data.Article;
import com.example.myreader.data.Category;
import com.example.myreader.data.CategoryHandler;
import com.example.myreader.data.Publisher;
import com.example.myreader.util.EfficientAdapter;
import com.example.myreader.util.ExpandableCategoryAdaptor;
import com.example.myreader.util.RssService;


public class Menu extends ListActivity {
	
	public final static String CONTENT = "com.example.MyReader.ARTICLE_CONTENT";
	public final static String URL = "com.example.MyReader.ARTICLE_URL";
	public final static String TITLE = "com.example.MyReader.ARTICLE_TITLE";
	public final static String DATE = "com.example.MyReader.ARTICLE_DATE";
	public final static String DESCRIPTION = "com.example.MyReader.ARTICLE_DESCRIPTION";
	public final static String ARTICLE_OBJECT = "com.example.MyReader.ARTICLE_OBJECT";
	public final static int CONTEXT_MARKREAD = 1;
	public final static int CONTEXT_MARKUNREAD = 2;
	public final static int CONTEXT_MARKHIDDEN = 3;
	public final static int CONTEXT_MARKVISIBLE = 4;
	
	List<String> mGroupName;
    HashMap<String, List<String>> mChildData;
	private List<Article> articles = new ArrayList<Article>();
	public ProgressDialog ShowProgress;
	private String mCategoryName;
	private String mPublisherName;

	private ExpandableListView mDrawerList;
	private ListView mThisListView;
	private DrawerLayout mDrawerLayout;
	private ArticleDbAdaptor mArticleDb;
	private EfficientAdapter mArticleListAdaptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		mThisListView = this.getListView();
		registerForContextMenu(mThisListView);
		
		Intent intent = getIntent();
		mCategoryName = intent.getStringExtra(CategoryListActivity.CATEGORY_NAME);
		mPublisherName = intent.getStringExtra(CategoryListActivity.PUBLISHER_NAME);
		mArticleDb = new ArticleDbAdaptor(this);
		
		ShowProgress = new ProgressDialog(Menu.this);
		ShowProgress.setMessage("Loading...");
		
		setupDrawerList();
		setupListeners();
		setupActionBar();
		setupRSSService();
    
	}
	
	private void onCategoryChange(String category, String publisher){
		this.mCategoryName = category;
		this.mPublisherName = publisher;
		setupActionBar();
		setupRSSService();	
	}

	private void setupRSSService(){
		RssService service = new RssService(this);
		if(mCategoryName.equals(Category.TopStories)){
			String[] feeds = {Publisher.YahooL, Publisher.TechCrunchL};
			service.execute(feeds);
		}else if(mCategoryName.equals(Category.Technology)){
			if(mPublisherName.equals(Publisher.TechCrunch)){
				String[] feeds = {Publisher.TechCrunchL};
				service.execute(feeds);
			}else if(mPublisherName.equals(Publisher.Engadget)){
				String[] feeds = {Publisher.EngadgetL};
				service.execute(feeds);
			}else if(mPublisherName.equals(Publisher.CNET)){
				String[] feeds = {Publisher.CNETL};
				service.execute(feeds);
			}
		}else if(mCategoryName.equals(Category.USNews)){
			if(mPublisherName.equals(Publisher.Yahoo)){
				String[] feeds = {Publisher.YahooL};
				service.execute(feeds);
			}else if(mPublisherName.equals(Publisher.CNN)){
				String[] feeds = {Publisher.CNNL};
				service.execute(feeds);
			}	
		}else if(mCategoryName.equals(Category.WorldNews)){
			if(mPublisherName.equals(Publisher.Google)){
				String[] feeds = {Publisher.GoogleL};
				service.execute(feeds);
			}else if(mPublisherName.equals(Publisher.NYT)){
				String[] feeds = {Publisher.NYTL};
				service.execute(feeds);
			}
		}
	}
	
	private void setupDrawerList(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        //CategoryAdaptor categoryListAdaptor = new CategoryAdaptor(this, android.R.layout.simple_list_item_1, categoryList);
		
		CategoryHandler.prepareListData();
		mGroupName = CategoryHandler.getGroupData();
		mChildData = CategoryHandler.getChildData();
		ExpandableCategoryAdaptor categoryListAdaptor = new ExpandableCategoryAdaptor(this, mGroupName, mChildData);
		
		
        mDrawerList.setAdapter(categoryListAdaptor);
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());	
        mDrawerList.setOnChildClickListener(new OnChildClickListener());
	}
	
	public void setRSSResult(List<Article>  articles){
		mArticleListAdaptor = new EfficientAdapter(this, android.R.layout.simple_list_item_1, articles);
		setListAdapter(mArticleListAdaptor);
		this.articles = articles;
		mArticleListAdaptor.notifyDataSetChanged();
	}
	
	private void setupListeners(){
		mThisListView.setLongClickable(true);	
		mThisListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				return false;
			}   
		});	
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.e("MENU CLICK", articles.get(position).getUrl());
	
		Article currArticle = articles.get(position);	
		
		mArticleDb.openToWrite();
		mArticleDb.markAsRead(currArticle.getGuid());
		mArticleDb.close();
		currArticle.setRead(true);
		
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(ARTICLE_OBJECT, currArticle);
		startActivity(intent);    
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Article article = (Article) getListAdapter().getItem(info.position);
		
		menu.setHeaderTitle("Article");
		if(article.getRead()){
			menu.add(0, CONTEXT_MARKUNREAD, 0, "Mark as unread");
		}else{
			menu.add(0, CONTEXT_MARKREAD, 0, "Mark as read");
		}
		
		if(article.getHidden()){
			menu.add(0, CONTEXT_MARKVISIBLE, 0, "Mark as visible");
		}else{
			menu.add(0, CONTEXT_MARKHIDDEN, 0, "Mark as hidden");
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
	    try {
	        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	    } catch (ClassCastException e) {
	        return false;
	    }
	    
	    Article article;
	    switch (item.getItemId()) {	        
	    	case CONTEXT_MARKREAD:
	    		article = (Article) getListAdapter().getItem(info.position);
	            mArticleDb.openToWrite();
	    		mArticleDb.markAsRead(article.getGuid());
	    		mArticleDb.close();
	    		article.setRead(true);
		    	mArticleListAdaptor.notifyDataSetChanged();
	            return true;
	    	case CONTEXT_MARKUNREAD:
	    		article = (Article) getListAdapter().getItem(info.position);
	            mArticleDb.openToWrite();
	    		mArticleDb.markAsUnread(article.getGuid());
	    		mArticleDb.close();
	    		article.setRead(false); 
		    	mArticleListAdaptor.notifyDataSetChanged();
	            return true;
	        case CONTEXT_MARKHIDDEN:
	        	article = (Article) getListAdapter().getItem(info.position);
	        	mArticleDb.openToWrite();
	    		mArticleDb.markAsHidden(article.getGuid());
	    		mArticleDb.close();
	    		article.setHidden(true); 
	    		mArticleListAdaptor.notifyDataSetChanged();
	            return true;
	        case CONTEXT_MARKVISIBLE:
	        	article = (Article) getListAdapter().getItem(info.position);
	        	mArticleDb.openToWrite();
	    		mArticleDb.markAsVisible(article.getGuid());
	    		mArticleDb.close();
	    		article.setHidden(false); 
	    		mArticleListAdaptor.notifyDataSetChanged();
	            return true;
	        default:
	        	return false;
	    }	    
	}
	
	
	
	/** Set up the Action Bar.
	 * Icon for up navigation is enabled.
	 * Label is the category name.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		getActionBar().setTitle(mCategoryName);
	}
		
	
	@Override
	public void onResume() {
	    super.onResume();
	    if (mArticleListAdaptor != null) {
	    	mArticleListAdaptor.notifyDataSetChanged();
	    }
	}
	
	
	
	/*
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) { 
	    	if(!categoryList[position].equals(categoryName)){
	    		onCategoryChange(categoryList[position]);
	    	}
			mDrawerLayout.closeDrawer(mDrawerList);
	    }
	}
	*/
	
	private class OnChildClickListener implements ExpandableListView.OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			String clickedCategory = mGroupName.get(groupPosition);
			String clickedPublisher =  mChildData.get(mGroupName.get(groupPosition)).get(childPosition);
			if(!clickedCategory.equals(mCategoryName)){
	    		onCategoryChange(clickedCategory, clickedPublisher);
	    	}
			mDrawerLayout.closeDrawer(mDrawerList);
			return false;
		}
	}
	
}
