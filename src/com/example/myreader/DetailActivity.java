package com.example.myreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.myreader.data.Article;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends Activity   {
	
	private Article article;
	private String articleURL;
	private String articleTitle;
	private String articlePubDate;
	private String articleDescription;
	private String scrapedContent;

	SpannableStringBuilder htmlSpannable ;
	public ProgressDialog ShowProgress;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		setupActionBar();
		
		Intent intent = getIntent();
		article = intent.getParcelableExtra(Menu.ARTICLE_OBJECT);
		
		ShowProgress = new ProgressDialog(DetailActivity.this);
		ShowProgress.setMessage("Loading...");
		
		setupHTMLService();
		setupArticleProperties();
		setupUIViews();
		
	}
	
	private void setupHTMLService(){
		//HtmlService service = new HtmlService(this, article);
		//service.execute(article.getUrl());
	}
	
	private void setupArticleProperties(){
		articleURL = article.getUrl();
		articleTitle = article.getTitle();
		articlePubDate = article.getPubDate();
		articleDescription = article.getDescription();
	}
	
	private void setupUIViews(){
		/* TITLE */
		TextView titleText = (TextView) findViewById(R.id.article_title);
	    titleText.setText(articleTitle);
	    
	    
	    /* DATE */
	    SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
	    String returndate;
	    try {
	    	Date date = formatter.parse(articlePubDate);
	    	Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        returndate=""+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
	        TextView dateText = (TextView) findViewById(R.id.article_date);
	 	    dateText.setText(returndate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	    /* DESCRIPTION */
		TextView descriptionText = (TextView) findViewById(R.id.article_description);
		descriptionText.setTextSize(15);
		
		//URLImageParser p = new URLImageParser(descriptionText, this);
		Spanned spanned = Html.fromHtml(articleDescription, null ,null);
		descriptionText.setText(spanned);
		descriptionText.setMovementMethod(LinkMovementMethod.getInstance());
		stripUnderlines(descriptionText);
	}
	

	public void setArticleContent(String content){
		this.scrapedContent = content;
	}

	
	
	/** Set up the Action Bar.
	 * Icon for up navigation is enabled.
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
	          //NavUtils.navigateUpTo(this, new Intent(this, Menu.class));
	    	  Intent intent = new Intent(this, Menu.class);
	    	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    	  startActivity(intent);
	    	  finish();
	         return true;
	   }
	   return super.onOptionsItemSelected(item);
	}

	
	/** Called when the user clicks the View Article button.
	 *  Will start the WebView activity to display the full article.
	 * 
	 * @param view
	 */
	public void viewArticle(View view) {
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra(Menu.URL, articleURL);
	    startActivity(intent);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** Finds and replaces the URLSpan instances with versions that don't underline.
	 * 
	 * @param textView
	 */
	private void stripUnderlines(TextView textView) {
	    Spannable s = (Spannable)textView.getText();
	    URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
	    for (URLSpan span: spans) {
	        int start = s.getSpanStart(span);
	        int end = s.getSpanEnd(span);
	        s.removeSpan(span);
	        span = new URLSpanNoUnderline(span.getURL());
	        s.setSpan(span, start, end, 0);
	    }
	    textView.setText(s);
	}
	 
	
	/** Customized version of URLSpan which doesn't enable the TextPaint's "underline" property
	 * 
	 */
	 private class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
	 
	 
	 
	 
	 
	 
	 
	 @SuppressWarnings("deprecation")
	public class URLDrawable extends BitmapDrawable {
		    // the drawable that you need to set, you could set the initial drawing
		    // with the loading image if you need to
		    protected Drawable drawable;

		    @Override
		    public void draw(Canvas canvas) {
		        // override the draw to facilitate refresh function later
		        if(drawable != null) {
		            drawable.draw(canvas);
		        }
		    }
		}
	 
	 
	 
	 
	 
	 public class URLImageParser implements ImageGetter {
		    Context c;
		    View container;

		    /***
		     * Construct the URLImageParser which will execute AsyncTask and refresh the container
		     * @param t
		     * @param c
		     */
		    public URLImageParser(View t, Context c) {
		        this.c = c;
		        this.container = t;
		    }

		    public Drawable getDrawable(String source) {
		        URLDrawable urlDrawable = new URLDrawable();

		        // get the actual source
		        ImageGetterAsyncTask asyncTask = 
		            new ImageGetterAsyncTask( urlDrawable);

		        asyncTask.execute(source);

		        // return reference to URLDrawable where I will change with actual image from
		        // the src tag
		        return urlDrawable;
		    }

		    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
		        URLDrawable urlDrawable;

		        public ImageGetterAsyncTask(URLDrawable d) {
		            this.urlDrawable = d;
		        }

		        @Override
		        protected Drawable doInBackground(String... params) {
		            String source = params[0];
		            return fetchDrawable(source);
		        }

		        @Override
		        protected void onPostExecute(Drawable result) {
		            // set the correct bound according to the result from HTTP call
		            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 
		                    + result.getIntrinsicHeight()); 

		            // change the reference of the current drawable to the result
		            // from the HTTP call
		            urlDrawable.drawable = result;

		            // redraw the image by invalidating the container
		            URLImageParser.this.container.invalidate();
		        }

		        /***
		         * Get the Drawable from URL
		         * @param urlString
		         * @return
		         */
		        public Drawable fetchDrawable(String urlString) {
		            try {
		                InputStream is = fetch(urlString);
		                Drawable drawable = Drawable.createFromStream(is, "src");
		                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 
		                        + drawable.getIntrinsicHeight()); 
		                return drawable;
		            } catch (Exception e) {
		                return null;
		            } 
		        }

		        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
		            DefaultHttpClient httpClient = new DefaultHttpClient();
		            HttpGet request = new HttpGet(urlString);
		            HttpResponse response = httpClient.execute(request);
		            return response.getEntity().getContent();
		        }
		    }
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
