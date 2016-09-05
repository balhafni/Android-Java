package com.example.graphicsanimation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SpriteSheetBetter extends Activity implements OnTouchListener {

	TheView v;
	Bitmap guy;
	float x, y;
	// location of the guy in the image
	Point[] guyLocations;
	// used for the array
	int loc = 0;
	// 15 frames per seconds
	float skipTime = 1000.0f / 15.0f; // 300000000;//1000 / 60.0f;
	long lastUpdate;
	float dt;
	int skipp = 0;

	Paint paintt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.spritesheetbetter);
		setGuyLocations();
		// make sure there is only ONE copy of the image and that the image
		// is in the drawable-nodpi. if it is not unwanted scaling might occur
		guy = BitmapFactory.decodeResource(getResources(), R.drawable.runjones1);
		x = y = 0;
		lastUpdate = 0;// System.nanoTime();
		paintt = new Paint();
		paintt.setColor(Color.WHITE);
		// out custom view
		v = new TheView(this);
		v.setOnTouchListener(this);

		setContentView(v);

	}

	public void setGuyLocations() {
		guyLocations = new Point[10];
		for (int i = 0, amt = 0; i < 960; i += 96, amt++) {
			guyLocations[amt] = new Point(i, 0);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		v.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			x = event.getX();
			y = event.getY();
			v.performClick();
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			break;
		}
		return true;
	}

	// surface view used so we can draw
	public class TheView extends SurfaceView implements Runnable {
		// resize and edit pixels in a surface
		SurfaceHolder holder;
		Boolean change = false;
		Thread t = null;

		public TheView(Context context) {
			super(context);
			// get this holder
			holder = getHolder();
		}

		@Override
		public void run() {
			while (change == true) {
				// perform drawing
				if (!holder.getSurface().isValid()) {
					continue;
				}

				dt = System.currentTimeMillis() - lastUpdate;
				// Log.d("d", dt+" "+"latupdate: "+ lastUpdate);
				// skipp++;
				if (dt >= skipTime) {
					// look it to paint on it
					Canvas c = holder.lockCanvas();
					// draw the background color
					c.drawARGB(255, 150, 150, 10);
					// create a rectangle on the position of the frame that i
					// want to draw from the original image
					Rect cut = new Rect(guyLocations[loc].x, guyLocations[loc].y, guyLocations[loc].x + 96, 96);
					// place the cut image and scale it in this rectangle
					Rect place = new Rect((int) x, (int) y, (int) x + 200, (int) y + 200);
					// draw the rectangle in the canvas with a white background
					c.drawRect(place, paintt);
					// cut the image using the cut rectangle size provided then
					// place it in the position where the place rectangle is in
					// the canvas
					c.drawBitmap(guy, cut, place, null);
					// c.drawBitmap(guy,x - (guy.getWidth()/2),y -
					// (guy.getHeight()/2),null);
					holder.unlockCanvasAndPost(c);
					// Log.e("xx","skipp:"+skipp);
					loc = ((loc + 1) % 10);
					lastUpdate = System.currentTimeMillis();
				}
			}

		}

		public void pause() {
			// stop drawing
			change = false;
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t = null;
		}

		public void resume() {
			change = true;
			t = new Thread(this);
			t.start();
		}

		@Override
		public boolean performClick() {
			super.performClick();
			return true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touchrotation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// return true;
			// Intent tt = new
			// Intent(SpriteSheetBetter.this,SpriteSheetBetter.class);
			// startActivity(tt);
		}
		return super.onOptionsItemSelected(item);
	}

}
