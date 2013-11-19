package com.letsnurture.CartoonBook;


import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SlideShowActivity extends Activity{

	private ViewFlipper flipper;
	private TextView txtAnimalName;
    int i=0;
    private ImageView iv;
	private int p;
	public int k=0;
	public int count=0;
	 Uri uri;
	 String path;
	 Context context;
	 Bitmap icon;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        Button btScontact = (Button) findViewById(R.id.btnScontact);
        Button btSgallery = (Button) findViewById(R.id.btnSgallery);
        Button btSquiz = (Button) findViewById(R.id.btnSquiz);
        this.txtAnimalName = (TextView) this.findViewById(R.id.textAnimalName);
        getOtherApp();
        btSgallery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(SlideShowActivity.this,CartoonActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
			}
		});
        btScontact.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(SlideShowActivity.this,ContactDetailActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
			}
		});
        btSquiz.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(SlideShowActivity.this,QuizActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
			}
		});
	}
    public void getOtherApp() 
	{
		flipper=(ViewFlipper) findViewById(R.id.flipperOtherApp);
		setUpAdSlider(flipper);

		for(p=0;p<PublicClass.pathint.length;p++)
		{
			try
			{
				iv=new ImageView(SlideShowActivity.this);
				//path =(PublicClass.pathint[p]).toString();
//				iv.setLayoutParams(new LayoutParams(R.dimen.image_default_width,R.dimen.image_default_height));
//				iv.setBackgroundResource(PublicClass.pathint[p]);
				//((BitmapDrawable) iv.getDrawable()).getBitmap().recycle();]
				//Drawable draw = getResources().getDrawable(PublicClass.pathint[p]);
				//InputStream is = getResources().openRawResource(PublicClass.pathint[p]);
				//Bitmap pisc = BitmapFactory.decodeStream(new BufferedInputStream(is));
			
				Resources res = getResources();
					res.getDrawable(PublicClass.pathint[p]);
					icon = Bitmap(res, 250, 250);
				
				
				iv.setImageResource(PublicClass.pathint[p]);
				iv.setImageBitmap(icon);
				
				flipper.addView(iv);
			
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	private void setUpAdSlider(final ViewFlipper flipper)
	{
		(new AsyncTask<Void, Void, Void>() 
		{
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				txtAnimalName.setText(PublicClass.namestr[0]);
				Typeface typface=Typeface.createFromAsset(getAssets(),"font/comicbd.ttf");

				  txtAnimalName.setTypeface(typface);

			}
			@Override
			protected Void doInBackground(Void... params) 
			{
				while (true) 
				{
					try 
					{
						Thread.sleep(4000);
						publishProgress();
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
			@Override
			protected void onPostExecute(Void result)
			{
				super.onPostExecute(result);
			}
			
			@Override
			protected void onProgressUpdate(Void... values)
			{
				super.onProgressUpdate(values);
				mySlideShow(flipper);
				if(count>=39)
				{
					count=-1;
				}
				count++;
				txtAnimalName.setText(PublicClass.namestr[count]);
				RunAnimations();
				Typeface typface=Typeface.createFromAsset(getAssets(),"font/comicbd.ttf");
				txtAnimalName.setTypeface(typface);
			}
		}).execute();
	}
	private void mySlideShow(ViewFlipper flipper)
	{
		AdViewAmination animObj = new AdViewAmination();
		flipper.setInAnimation(animObj.getInAnimation());
		
		flipper.setOutAnimation(animObj.getOutAnimation());
		flipper.showNext();
	}
	private void RunAnimations() {
		// TODO Auto-generated method stub
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();
      //  TextView tv = (TextView) findViewById(R.id.firstTextView);
        txtAnimalName.clearAnimation();
        txtAnimalName.startAnimation(a);
	}
	
//	
//	@Override
//	public void onBackPressed() 
//	{
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SlideShowActivity.this);
//		alertDialogBuilder.setTitle("Slide Show");
//		alertDialogBuilder
//				.setMessage("Click yes to exit!")
//				.setCancelable(false)
//				.setPositiveButton("Yes",
//						new DialogInterface.OnClickListener() 
//						{
//							public void onClick(DialogInterface dialog, int id) {
//								Intent i = new Intent(SlideShowActivity.this,CartoonActivity.class);
//								startActivity(i);
//								finish();
//							}
//						})
//				.setNegativeButton("No", new DialogInterface.OnClickListener() 
//				{
//					public void onClick(DialogInterface dialog, int id) 
//					{
//						dialog.cancel();
//					}
//				});
//		AlertDialog alertDialog = alertDialogBuilder.create();
//		alertDialog.show();
//	}
    @Override
   	public boolean onCreateOptionsMenu(Menu menu)
   	{
   		MenuInflater inflater = getMenuInflater();
   		inflater.inflate(R.menu.menu, menu);
   		return super.onCreateOptionsMenu(menu);
   	}

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item)
   	{
   		switch (item.getItemId())
   		{
   	        case R.id.rateApp:
   	        	Intent viewTwitter =new Intent("android.intent.action.VIEW",Uri.parse("market://details?id=com.letsnurture.cartoonbook"));
        			startActivity(viewTwitter);
        			overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        			return true;
   	        default:
   	            return super.onOptionsItemSelected(item);
   		}
   	}
   	
 	private Bitmap Bitmap(Resources res,  int id2, int id3) {
		// TODO Auto-generated method stub
 		
		 // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable= true;
	    options.inJustDecodeBounds = true;
	    //BitmapFactory.decodeResource(res, resId, options);
	   BitmapFactory.decodeResource(res,(Integer) null, options);
	    // Calculate inSampleSize
	    //options.inSampleSize = calculateInSampleSize(options, id2, id3);
	   	options.inSampleSize = calculateInSampleSize(options, id2, id3);
	   
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	   
	    return  BitmapFactory.decodeResource(getResources(), (Integer) null, options);
		
	}

	@SuppressWarnings("unused")
	private int calculateInSampleSize(BitmapFactory.Options options, int i, int j) {
		// TODO Auto-generated method stub
		 // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > j || width > i) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > j
	                && (halfWidth / inSampleSize) > i) {
	            inSampleSize *= 2;
	        }
	    }
	    return inSampleSize;
		
		
			}
}
