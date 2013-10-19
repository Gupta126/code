import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.letsnurture.classes.PublicClass;

public class Wallpaper extends Activity
{
	private ImageView imageView;
	private int i=0;
	private int imageresource;
	private ImageButton download;
	private ImageButton wallpaper;
	private TextView tcat;
	private int imageid[];
    RefreshHandler refreshHandler=new RefreshHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	Typeface rt=Typeface.createFromAsset(getAssets(),"font/Carleton.ttf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper);
		tcat = (TextView) findViewById(R.id.textTitle);
		tcat.setText(PublicClass.catName);
		tcat.setTypeface(rt);
        imageView=(ImageView)findViewById(R.id.imageView1);
        checkCat();
        updateUI();
        imageView.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v)
			{
				// set up dialog
				final Dialog dialog = new Dialog(Wallpaper.this,android.R.style.Theme_Translucent_NoTitleBar);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom_dialog1);
				dialog.setCancelable(true);
				dialog.show();
				final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
				
				Runnable hideDialog= new Runnable()
				{
				    public void run()
				    {
				       dialog.dismiss();
				    }
				};
				executor.schedule(hideDialog, 3, TimeUnit.SECONDS);
				download=(ImageButton)dialog.findViewById(R.id.download);
				wallpaper=(ImageButton)dialog.findViewById(R.id.wallpaper);
				Drawable drawable = getResources().getDrawable(imageresource);
				final Bitmap bm = ((BitmapDrawable)drawable).getBitmap();
				
				download.setOnClickListener(new OnClickListener() 
				{
					public void onClick(View v) 
					{
						dialog.dismiss();
						OutputStream outStream = null;
						String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
						File file = new File(extStorageDirectory, "festival -" + imageresource + ".png");
						try 
						{
						    outStream = new FileOutputStream(file);
						    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
						    outStream.flush();
						    outStream.close();
						    Toast.makeText(Wallpaper.this, "Image Downloaded.", Toast.LENGTH_LONG).show();
						}
						catch (FileNotFoundException e) 
						{
							e.printStackTrace();
						}
						catch (Exception e) 
						{
						    e.printStackTrace();
						}

					}
				});
				wallpaper.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v) 
					{
						int count = imageresource;
						dialog.dismiss();
						Bitmap b = BitmapFactory.decodeResource(getResources(), count);
						try {
							    String path = Images.Media.insertImage(getContentResolver(), b,"title", null);
							    Uri screenshotUri = Uri.parse(path);
							    final Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
							    emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							    emailIntent1.putExtra(Intent.EXTRA_TEXT, "Diwali Dhamal WallPaper");
							    emailIntent1.putExtra(Intent.EXTRA_STREAM, screenshotUri);
							    emailIntent1.setType("image/png");
							    startActivity(Intent.createChooser(emailIntent1, "Send email using"));
							}
							catch(Exception e) { }
					}
				});
			}
		});
    }
    class RefreshHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
	        try
	        {
	        	Wallpaper.this.updateUI();
			} 
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        }
        }
        public void sleep(long delayMillis)
        {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };

    public void updateUI()
    {
        refreshHandler.sleep(2500);
        if(i<imageid.length)
        {
            imageView.setImageResource(imageid[i]);
            imageresource=imageid[i];
            i++;
            if(i == imageid.length)
            {
            	i=0;
            }
        }
    }
    public void checkCat()
	  {
    	i = 0;
		  if(PublicClass.catName.equals("Bhai Beej"))
		  {
			  imageid = PublicClass.imageBhaibij;
		  }
		  else if(PublicClass.catName.equals("Dev Diwali"))
		  {
			  imageid = PublicClass.imageDevdiwali;
		  }
		  else if(PublicClass.catName.equals("Dhan Teras"))
		  {
			  imageid = PublicClass.imageTeras;
		  }
		  else if(PublicClass.catName.equals("Diwali"))
		  {
			  imageid = PublicClass.imageDiwali;
		  }
		  else if(PublicClass.catName.equals("New Year"))
		  {
			  imageid = PublicClass.imageNewyear;
		  }
		  else  if(PublicClass.catName.equals("Kali Chaudas"))
		  {
			  imageid = PublicClass.imageChaudas;
		  }
		  else
			  finish();
	  }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.tempView));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
