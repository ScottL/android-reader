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
import android.widget.ListView;

import com.example.database.ArticleDbAdaptor;
import com.example.myreader.data.Article;
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

	private List<Article> articles = new ArrayList<Article>();
	public ProgressDialog ShowProgress;
	private String categoryName;

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private String[] categoryList = CategoryListActivity.CATEGORY_LIST;
	private ArticleDbAdaptor mArticleDb;
	private EfficientAdapter articleListAdaptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		
		Intent intent = getIntent();
		categoryName = intent.getStringExtra(CategoryListActivity.CATEGORY_NAME);
		mArticleDb = new ArticleDbAdaptor(this);
		
		ShowProgress = new ProgressDialog(Menu.this);
		ShowProgress.setMessage("Loading...");
		
		setupDrawerList();
		setupActionBar();
		setupRSSService();
    
		
		/*	http://news.yahoo.com/rss/us
		 *	http://news.yahoo.com/rss/
		 *	http://rss.nytimes.com/services/xml/rss/nyt/US.xml
		 *	http://feeds.feedburner.com/TechCrunch
		 *
		 */
	}
	
	private void onCategoryChange(String category){
		this.categoryName = category;
		setupActionBar();
		setupRSSService();	
	}

	private void setupRSSService(){
		RssService service = new RssService(this);
		if(categoryName.equals("Top Stories")){
			String[] feeds = {"http://news.yahoo.com/rss/us"};
			service.execute(feeds);
		}else if(categoryName.equals("Technology")){
			String[] feeds = {"http://feeds.feedburner.com/TechCrunch"};
			service.execute(feeds);
		}else{
			String[] feeds = {"http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&output=rss"};
			service.execute(feeds);
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
		articleListAdaptor = new EfficientAdapter(this, android.R.layout.simple_list_item_1, articles);
		setListAdapter(articleListAdaptor);
		this.articles = articles;
		articleListAdaptor.notifyDataSetChanged();
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
		
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View v, int position, long id) { 
	    	if(!categoryList[position].equals(categoryName)){
	    		onCategoryChange(categoryList[position]);
	    	}
			mDrawerLayout.closeDrawer(mDrawerList);
	    }
	}
	
	@Override
	public void onStart() {
	    super.onStart();

	    if (articleListAdaptor != null) {
	    	articleListAdaptor.notifyDataSetChanged();
	    }
	}
	
	
}
