package com.example.graphicsanimation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class TransDifferentSize extends Activity {
ImageView img;
Animation bigsmall;
AnimationDrawable runAnimation;
TransitionDrawable trans;
Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trans_different);
		img=(ImageView)findViewById(R.id.sameSize);
	//	img.setBackgroundResource(R.drawable.run_anim);
		//runAnimation = (AnimationDrawable)img.getBackground();
		//bigsmall = AnimationUtils.loadAnimation(this, R.animator.bigsmall);
		trans = (TransitionDrawable)getResources().getDrawable(R.drawable.transitions_different);
		img.setImageDrawable(trans);
		btn = (Button)findViewById(R.id.xfadebtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//img.startAnimation(bigsmall);	
				trans.startTransition(10000);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transd, menu);
		return true;
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		//runAnimation.start();
		//img.startAnimation(bigsmall);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//return true;
			Intent tt = new Intent(TransDifferentSize.this,TwoViews.class);
			startActivity(tt);
		}
		return super.onOptionsItemSelected(item);
	}
}
