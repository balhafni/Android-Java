package com.example.class05_2016;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;


public class SimpleAnimations extends Activity{
	ImageView shakeImage,tenpView,thirtyView, fortyView, fiftyView, sixtypView; 
	Animation tweenit,fadein,fadeout,scale,rotate,slide,move;
	Animator shakeit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleanimation_layout);
		fadein = AnimationUtils.loadAnimation(this, R.animator.fadein);//fix shake
		fadeout = AnimationUtils.loadAnimation(this, R.animator.fadeout);
		tweenit = AnimationUtils.loadAnimation(this, R.animator.tweenit);
		scale = AnimationUtils.loadAnimation(this, R.animator.scale);
		rotate = AnimationUtils.loadAnimation(this, R.animator.rotate);
		slide = AnimationUtils.loadAnimation(this, R.animator.slide);
		move = AnimationUtils.loadAnimation(this, R.animator.move);
		shakeImage = (ImageView)findViewById(R.id.imageView1);
		tenpView = (ImageView)findViewById(R.id.tenView);
		thirtyView = (ImageView)findViewById(R.id.thirtyView);
		fortyView =  (ImageView)findViewById(R.id.fortyView);
		fiftyView =  (ImageView)findViewById(R.id.fiftyView);
		sixtypView = (ImageView)findViewById(R.id.sixtyView);
		
		shakeit = (AnimatorSet)AnimatorInflater.loadAnimator(this, R.animator.shakeit);
		shakeit.setTarget(shakeImage);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		
		LayoutParams pp = new LayoutParams((int)(width*.4),(int)(width*.4));
		pp.setMargins((int)(width*.30), (int)(height*.32), 0, 0);
		shakeImage.setLayoutParams(pp);
		
		LayoutParams tenp = new LayoutParams((int)(width*.1),(int)(height*.3));
		tenp.setMargins((int)(width*.1), (int)(height*.1), 0, 0);
		tenpView.setLayoutParams(tenp);
		
		LayoutParams thrityp = new LayoutParams((int)(width*.1),(int)(height*.3));
		thrityp.setMargins((int)(width*.3), (int)(height*.1), 0, 0);
		thirtyView.setLayoutParams(thrityp);
		
		
		LayoutParams fortyp = new LayoutParams((int)(width*.1),(int)(height*.3));
		fortyp.setMargins((int)(width*.4), (int)(height*.1), 0, 0);
		fortyView.setLayoutParams(fortyp);
		
		LayoutParams fiftyp = new LayoutParams((int)(width*.1),(int)(height*.3));
		fiftyp.setMargins((int)(width*.5), (int)(height*.1), 0, 0);
		fiftyView.setLayoutParams(fiftyp);
		
		LayoutParams sixtyp = new LayoutParams((int)(width*.1),(int)(height*.3));
		sixtyp.setMargins((int)(width*.6), (int)(height*.1), 0, 0);
		sixtypView.setLayoutParams(sixtyp);
		
		slide.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	AnimationListener anim = new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			
			
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main4, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.tween) {
			shakeImage.startAnimation(tweenit);
		}
		else if (id ==R.id.fadein)
			shakeImage.startAnimation(fadein);
		else if (id ==R.id.fadeout)
			shakeImage.startAnimation(fadeout);
		else if (id ==R.id.scale)
			shakeImage.startAnimation(scale);
		else if (id ==R.id.rotate)
			shakeImage.startAnimation(rotate);
		else if (id ==R.id.slide)
			shakeImage.startAnimation(slide);
		else if (id ==R.id.move)
			shakeImage.startAnimation(move);
		else if (id ==R.id.shake)
			shakeit.start();
		return super.onOptionsItemSelected(item);
	}
}
