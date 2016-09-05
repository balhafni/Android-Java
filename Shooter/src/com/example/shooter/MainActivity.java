package com.example.shooter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	MainView surf;
	GestureDetector gesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surf = new MainView(this);
		setContentView(surf);
		gesture = new GestureDetector(this, gestureListener);
	}

	SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onDoubleTap(MotionEvent event) {
			Log.v("gesture", "double tap");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
			Log.v("gesture", "onFling: " + event1.toString() + event2.toString());
			return true;
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			Log.v("gesture", "down down");
		} else if (action == MotionEvent.ACTION_MOVE)
			Log.v("gesture", "move move");
		return gesture.onTouchEvent(e);

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