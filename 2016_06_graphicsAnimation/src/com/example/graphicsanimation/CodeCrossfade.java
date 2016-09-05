package com.example.graphicsanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

public class CodeCrossfade extends Activity{
	
	 private View mContentView;
	 private View mLoadingView;
	 private int mShortAnimationDuration;
	 private Button fadeit,goDown,rotateit;
	 int width,height; 
	    @Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crossfade);
		
		mContentView = findViewById(R.id.spriteView1);
		mLoadingView = findViewById(R.id.imageView2);
		fadeit = (Button)findViewById(R.id.xfadebtn);
		goDown= (Button)findViewById(R.id.goDownbtn);
		rotateit = (Button)findViewById(R.id.rotatebtn);
		
		//get size of screen
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		Log.e("height",height+"");
		Log.e("width",width+"");
		
		mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
		Log.e("short animation time", mShortAnimationDuration+"");//200 mili
		
		// Initially hide the content view.
        mContentView.setVisibility(View.GONE);
        
        fadeit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startFades();
			}
		});
        //down
        goDown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BringItDown();
				
			}
		});
        //rotate
        rotateit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rotateit();
			}
		});
        
	    }
	    
	    public void startFades()
	    {
	    	// Set the content view to 0% opacity but visible, so that it is visible
	        // (but fully transparent) during the animation.
	        mContentView.setAlpha(0f);
	        mContentView.setVisibility(View.VISIBLE);
	        
	        // Animate the content view to 100% opacity, and clear any animation
	        // listener set on the view.
	        mContentView.animate()
	                .alpha(1f)
	                .setDuration(1000)//mShortAnimationDuration)
	                .setListener(null);
	      
	        
	        // Animate the loading view to 0% opacity. After the animation ends,
	        // set its visibility to GONE as an optimization step (it won't
	        // participate in layout passes, etc.)
	        mLoadingView.animate()
	                .alpha(0f)
	                .setDuration(mShortAnimationDuration)
	                .setListener(new AnimatorListenerAdapter() {
	                    @Override
	                    public void onAnimationEnd(Animator animation) {
	                        mLoadingView.setVisibility(View.GONE);
	                    }
	                });
	    }
	    
	    public void BringItDown()
	    {
	    	 int xValue = width - mContentView.getWidth();
	    	    int yValue = height - mContentView.getHeight();
	    	    Log.e("downheight",yValue+"");
	    		Log.e("downwidth",xValue+"");
	    	    mContentView.animate().x(xValue).y(yValue).setListener(new AnimatorListenerAdapter() {
	    	    	
	    	    	@Override
	    	    	public void onAnimationEnd(Animator animation){
	    	    		
	    	    		 int xValue = width/2 - mContentView.getWidth()/2;
	    		    	 int yValue = height/2 - mContentView.getHeight()/2;
	    		    	 Log.e("upheight",yValue+"");
	    		    		Log.e("upwidth",xValue+"");
	    		    	    mContentView.animate().x(xValue).y(yValue).setListener(null);//must be null or endless loop
	    	    	}
				});
	    
	    }
	    public void rotateit()
	    {
	    	mContentView.animate().rotationXBy(1080);
	    }
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.sprites, menu);
			return true;
		}
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				Intent tt = new Intent(CodeCrossfade.this,SpriteSheet.class);
				startActivity(tt);
			}
			return super.onOptionsItemSelected(item);
		}

}
