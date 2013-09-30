package com.example.myreader;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.database.ArticleDbAdaptor;
import com.example.myreader.data.Article;
import com.example.myreader.data.Category;
import com.example.myreader.data.Publisher;
import com.example.myreader.util.CategoryAdaptor;
import com.example.myreader.util.EfficientAdapter;
import com.example.myreader.util.RssService;


public class Menu extends ListActivity {
	
	public final static String CONTENT = "com.example.MyReader.ARTICLE_CONTENT";
	public final static String URL = "com.example.MyReader.ARTICLE_URL";
	public final static String TITLE = "com.example.MyReader.ARTICLE_TITLE";
	public final static String DATE = "com.example.MyReader.ARTICLE_DATE";
	public final static String DESCRIPTION = "com.example.MyReader.ARTICLE_DESCRIPTION";
	public final static String ARTICLE_OBJECT = "com.example.MyReader.ARTICLE_OBJECT";

	private String[] categoryList = Category.CategoryList;
	private List<Article> articles = new ArrayList<Article>();
	public ProgressDialog ShowProgress;
	private String categoryName;
	private String publisherName;

	private ListView mDrawerList;
	private ListView mThisListView;
	private DrawerLayout mDrawerLayout;
	private ArticleDbAdaptor mArticleDb;
	private EfficientAdapter mArticleListAdaptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		mThisListView = this.getListView();
		
		Intent intent = getIntent();
		categoryName = intent.getStringExtra(CategoryListActivity.CATEGORY_NAME);
		publisherName = intent.getStringExtra(CategoryListActivity.PUBLISHER_NAME);
		mArticleDb = new ArticleDbAdaptor(this);
		
		ShowProgress = new ProgressDialog(Menu.this);
		ShowProgress.setMessage("Loading...");
		
		setupDrawerList();
		setupListeners();
		setupActionBar();
		setupRSSService();
    
	}
	
	private void onCategoryChange(String category){
		this.categoryName = category;
		setupActionBar();
		setupRSSService();	
	}

	private void setupRSSService(){
		RssService service = new RssService(this);
		if(categoryName.equals(Category.TopStories)){
			String[] feeds = {Publisher.YahooL, Publisher.TechCrunchL};
			service.execute(feeds);
		}else if(categoryName.equals(Category.Technology)){
			if(publisherName.equals(Publisher.TechCrunch)){
				String[] feeds = {Publisher.TechCrunchL};
				service.execute(feeds);
			}else if(publisherName.equals(Publisher.Engadget)){
				String[] feeds = {Publisher.EngadgetL};
				service.execute(feeds);
			}else if(publisherName.equals(Publisher.CNET)){
				String[] feeds = {Publisher.CNETL};
				service.execute(feeds);
			}
		}else if(categoryName.equals(Category.USNews)){
			if(publisherName.equals(Publisher.Yahoo)){
				String[] feeds = {Publisher.YahooL};
				service.execute(feeds);
			}else if(publisherName.equals(Publisher.CNN)){
				String[] feeds = {Publisher.CNNL};
				service.execute(feeds);
			}	
		}else if(categoryName.equals(Category.WorldNews)){
			if(publisherName.equals(Publisher.Google)){
				String[] feeds = {Publisher.GoogleL};
				service.execute(feeds);
			}else if(publisherName.equals(Publisher.NYT)){
				String[] feeds = {Publisher.NYT};
				service.execute(feeds);
			}
		}
	}
	
	private void setupDrawerList(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
        CategoryAdaptor categoryListAdaptor = new CategoryAdaptor(this, android.R.layout.simple_list_item_1, categoryList);
        mDrawerList.setAdapter(categoryListAdaptor);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());	
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
				return true;
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
	
	
	/** Set up the Action Bar.
	 * Icon for up navigation is enabled.
	 * Label is the category name.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		getActionBar().setTitle(categoryName);
	}
		
	
	@Override
	public void onResume() {
	    super.onResume();
	    if (mArticleListAdaptor != null) {
	    	mArticleListAdaptor.notifyDataSetChanged();
	    }
	}
	
	
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) { 
	    	if(!categoryList[position].equals(categoryName)){
	    		onCategoryChange(categoryList[position]);
	    	}
			mDrawerLayout.closeDrawer(mDrawerList);
	    }
	}
	
}
