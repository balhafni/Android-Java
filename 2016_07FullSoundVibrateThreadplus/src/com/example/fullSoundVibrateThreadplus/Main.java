package com.example.fullSoundVibrateThreadplus;


import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class Main extends Activity{
	
	private GestureDetector gestureDetector; // listens for double taps
	private MainView surfView; // custom view to display the game
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surfView = new MainView(this);
		setContentView(surfView);
		// initialize the GestureDetector
		gestureDetector = new GestureDetector(this, gestureListener);
		// allow volume keys to set game volume
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
	}
	
	SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		// called when the user double taps the screen
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			surfView.shoot(e); // fire the cannonball
			return true; // the event was handled
		} // end method onDoubleTap
	}; // end gestureListener

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get int representing the type of action causing the event
		int action = event.getAction();
		Log.v("1", event.getX()+" "+event.getY());
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		Log.v("2", height+" "+width);
		if(event.getX()<width/8&&event.getY()<height/8){
			//cannonView.cannonThread.setRunning(!cannonView.cannonThread.getRunning());
		}else
		// the user user touched the screen or dragged along the screen
		if (action == MotionEvent.ACTION_DOWN
				|| action == MotionEvent.ACTION_MOVE) {
			surfView.moveScope(event); // align the cannon
		} // end if
			// call the GestureDetector's onTouchEvent method
		return gestureDetector.onTouchEvent(event);
	} // end method onTouchEvent
		// listens for touch events sent to the GestureDetector
}
