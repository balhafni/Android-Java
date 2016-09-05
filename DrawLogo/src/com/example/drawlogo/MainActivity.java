package com.example.drawlogo;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	Context context = this;
	ImageView imageView;
	Bitmap bitmap;
	Handler handler;
	Animation rotate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity);
		Logo logo = new Logo(context, 300, 500);
		setContentView(logo);
		rotate = AnimationUtils.loadAnimation(this, R.animator.rotate);
		rotate.setAnimationListener(new AnimationListener() {

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
				setContentView(R.layout.activity);

			}
		});
		logo.startAnimation(rotate);
		
//		handler = new Handler();
//		handler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				setContentView(R.layout.activity);
//
//			}
//		}, 2000);
		// imageView = (ImageView)findViewById(R.id.imageView1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
