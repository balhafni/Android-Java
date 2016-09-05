package com.example.graphicsanimation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SpriteSheet extends Activity {

	ImageView img;
	AnimationDrawable runAnimation;

	Animation bigsmall;
	Button big;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout
		setContentView(R.layout.spritesheet);
		// get image reference
		img = (ImageView) findViewById(R.id.spriteView1);
		// set the first image to be the background and also set the xml
		// animation
		// img.setBackgroundResource(R.drawable.run_anim);
		img.setImageResource(R.drawable.run_anim_same);
		// runAnimation = (AnimationDrawable)img.getBackground();
		runAnimation = (AnimationDrawable) img.getDrawable();

		big = (Button) findViewById(R.id.bigsmall);
		big.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runAnimation.stop();
				bigsmall = AnimationUtils.loadAnimation(SpriteSheet.this, R.animator.bigsmall);
				img.startAnimation(bigsmall);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spritesbetter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// return true;
			Intent tt = new Intent(SpriteSheet.this, SpriteSheetBetter.class);
			startActivity(tt);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		runAnimation.start();

	}

}
