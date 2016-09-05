package com.example.pewpew;

import android.app.ActionBar;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {
	private GestureDetector gestureDetector; // listens for double taps
	private CannonView cannonView;
	public static MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		player = MediaPlayer.create(MainActivity.this, R.raw.stop_the_music);
		cannonView = new CannonView(this);
		gestureDetector = new GestureDetector(this, gestureListener);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		hidingBars();
		setContentView(cannonView);

	}

	public void hidingBars() {
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}

	public void onPause() {
		super.onPause(); // call the super method
		cannonView.stopGame(); // terminates the game
	} // end method onPause

	// release resources
	@Override
	protected void onDestroy() {
		super.onDestroy();
		cannonView.releaseResources();
	} // end method onDestroy

	// called when the user touches the screen in this Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get int representing the type of action causing the event
		int action = event.getAction();
		// the user user touched the screen or dragged along the screen
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
			cannonView.alignCannon(event); // align the cannon
		} // end if
			// call the GestureDetector's onTouchEvent method
		return gestureDetector.onTouchEvent(event);
	} // end method onTouchEvent

	SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		// called when the user double taps the screen
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			cannonView.fireCannonball(e); // fire the cannonball
			return true; // the event was handled
		} // end method onDoubleTap
	}; // end gestureListener

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

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
}
