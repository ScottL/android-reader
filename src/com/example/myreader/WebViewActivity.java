package com.example.myreader;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity{
	
	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		setupActionBar();
		
		Intent intent = getIntent();
		String url = intent.getStringExtra(Menu.URL);

		webView = (WebView) findViewById(R.id.webview1);
		webView.loadUrl(url);
		
		//Override browser launcher when links are clicked within the WebView
		webView.setWebViewClient(new WebViewClient());
 
	}
	
	
	
	/** Set up the Action Bar.
	 *  Icon for up navigation is enabled.
	 *  
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch (item.getItemId()) {
	      case android.R.id.home:
	    	  Intent intent = new Intent(this, DetailActivity.class);
	    	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    	  startActivity(intent);
	    	  finish();
	         return true;
	   }
	   return super.onOptionsItemSelected(item);
	}

	
	
	/** Whenever the user press the BACK button the activity that handles the WebView is closed. 
	 * If you want to use BACK button to navigate from current opened link to previously opened
	 * link in WebView then you need to override the onKeyDown() inside the activity.
	 * 
	 * The condition uses a KeyEvent to check whether the key pressed is the BACK button and whether
	 * the WebView is actually capable of navigating back (if it has a history). 
	 * If both are not true, then it send the event up the chain (and the Activity will close). 
	 * But if both are true, then it call goBack(), which will navigate back one step in the history. 
	 * You can also navigate forward through the history with goForward() method.
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
	    	webView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
