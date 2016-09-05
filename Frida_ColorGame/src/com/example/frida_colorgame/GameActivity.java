package com.example.frida_colorgame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class GameActivity extends Activity {
	Paint myPaintRed, myPaintGreen, myPaintBlue, myPaintYellow, myRandomPaint;
	MyView view;
	float x, y;
	int width, height, colorInt;
	boolean startThread = true;
	boolean painting = false;
	boolean touched = false;
	boolean matched = false, yesmatch = false;
	GestureDetector gesture;
	Random randnr;
	int randnum, mynum;
	Handler handler;
	int level = 1;
	static int countTries, winning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		level = getIntent().getExtras().getInt("chosenLevel");
		view = new MyView(this);
		setContentView(view);
		x = y = 0;
		mynum = 0;
		randnr = new Random();
		randnum = randnr.nextInt(2);
		// gesture = new GestureDetector(this, gestureListener);
		countTries = 0;
	}

	// @Override
	// protected void onPause() {
	// super.onPause();
	// view.pause();
	// }

	// @Override
	// protected void onResume() {
	// super.onResume();
	// view.resume();
	// }

	// I modified the onTouchEvent! It's not doing anything except passing the
	// event to the gesture
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			Log.v("gesture", "down down");
		} else if (action == MotionEvent.ACTION_MOVE)
			Log.v("gesture", "move move");
		return view.getGesture(e);

	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// Log.v("touchh", "inner");
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// x = event.getX();
	// y = event.getY();
	// System.out.println(x + " " + y);
	// Log.v("down", "inner");
	//
	// colorInt = checkIfColorsArePressed(x, y);
	// painting = true;
	// startThread = true;
	// break;
	// }
	// return true;
	// }

	public int checkIfColorsArePressed(float x, float y) {
		view.thisY = (int) (0.1 * height);
		mynum = 0;// this number tells me if i want to draw the dot
		int num = -1;
		touched = true;
		if (y >= (int) (0.1 * height) && y <= (int) (0.5 * height)) {
			if (x >= (int) (0.075 * width) && x <= (int) (0.325 * width)) {
				num = 0;
				System.out.println("RED T");
				painting = true;
				touched = true;
				Log.v("pressedred", "REDDD");
			} else if (x >= (int) (0.375 * width) && x <= (int) (0.625 * width)) {
				num = 1;
				painting = true;
				touched = true;
				System.out.println("Green");
			} else if (x >= (int) (0.675 * width) && x <= (int) (0.925 * width)) {
				num = 2;
				painting = true;
				touched = true;
				System.out.println("Blue");
			}
		}

		return num;
	}

	public class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

		SurfaceHolder holder;
		Boolean change = false;
		Thread t = null;
		GestureDetector gesture;
		Paint circle, lines;
		// int thisY = (int)(0.1*height);
		int thisY;
		boolean drawCircle = false;
		boolean drawLines = false;
		long update, lastUpdate;
		float skipTime = 1000.0f / 40.0f; // 40 frames per second
		float dt;

		public MyView(Context context) {
			super(context);
			this.getHolder().addCallback(this);
			holder = getHolder();
			// thisY = (int)(0.1*height);

			myPaintRed = new Paint();
			myPaintGreen = new Paint();
			myPaintBlue = new Paint();
			myPaintYellow = new Paint();
			myRandomPaint = new Paint();
			myPaintRed.setColor(Color.RED);
			myPaintGreen.setColor(Color.GREEN);
			myPaintBlue.setColor(Color.BLUE);
			myPaintYellow.setColor(Color.YELLOW);
			myRandomPaint.setColor(generateColor());
			gesture = new GestureDetector(context, gestureListener);
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
				colorInt = checkIfColorsArePressed((int) event1.getX(), (int) event1.getY());
				// painting = true;
				startThread = true;

				return true;
			}

		};

		public int generateColor() {
			int col = -1;
			Random rand = new Random();
			int num = rand.nextInt(3);
			switch (num) {
			case 0:
				col = Color.RED;
				break;
			case 1:
				col = Color.GREEN;
				break;
			case 2:
				col = Color.BLUE;
				break;
			}
			return col;
		}

		// There's no need for a pause and resume! On you're calling join, the
		// thread is going crazy!

		// public void pause() {
		// // stop drawing
		// change = false;
		// try {
		// t.join();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// t = null;
		// }

		// public void resume() {
		// change = true;
		// t = new Thread(this);
		// Log.v("touch", "start");
		// t.start();
		// }

		// This method is called only once when the surface is created
		public void startThread(SurfaceHolder holder) {
			t = new Thread(this);
			t.start();

		}

		// just a simple method to add the MotionEvent to the gesture
		public boolean getGesture(MotionEvent e) {
			return gesture.onTouchEvent(e);
		}

		public void draw1(Canvas canvas) {

			mynum = 1;
			canvas.drawColor(Color.YELLOW);

			DisplayMetrics disMet = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(disMet);
			width = disMet.widthPixels;
			height = disMet.heightPixels;

			Rect placeRed = new Rect((int) (0.075 * width), (int) (0.1 * height), (int) (0.325 * width),
					(int) (0.3 * height));
			canvas.drawRect(placeRed, myPaintRed);

			Rect placeGreen = new Rect((int) (0.375 * width), (int) (0.1 * height), (int) (0.625 * width),
					(int) (0.3 * height));
			canvas.drawRect(placeGreen, myPaintGreen);

			Rect placeBlue = new Rect((int) (0.675 * width), (int) (0.1 * height), (int) (0.925 * width),
					(int) (0.3 * height));
			canvas.drawRect(placeBlue, myPaintBlue);

			Rect bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
			canvas.drawRect(bottomColor, myRandomPaint);// Should be changing
														// color after 2 sec
			Paint grey = new Paint();
			grey.setColor(Color.MAGENTA);

			Rect randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
			canvas.drawRect(randomline, grey);

			// if (matched && yesmatch) {
			// Paint paint = new Paint();
			// paint.setColor(Color.rgb(300, 150, 200));
			// paint.setStrokeWidth(15);
			// int startx = (int) (width * 0.2);
			// int starty = (int) (height * 0.3);
			// int endx = (int) (width * 0.8);
			// int endy = (int) (height * 0.6);
			// canvas.drawLine(startx, starty, endx, endy, paint);
			//
			// startx = (int) (width * 0.8);
			// starty = (int) (height * 0.6);
			// endx = (int) (width * 0.2);
			// endy = (int) (height * 0.3);
			// canvas.drawLine(startx, starty, endx, endy, paint);
			// yesmatch = false;
			// }
			if (drawCircle) {
				circle = new Paint();
				circle.setStyle(Paint.Style.STROKE);
				circle.setColor(Color.WHITE);
				circle.setStrokeWidth(20);
				canvas.drawCircle((int) (width * 0.5), (int) (height * 0.5), 400, circle);
			}
			if (drawLines) {
				lines = new Paint();
				lines.setColor(Color.RED);
				lines.setStyle(Paint.Style.STROKE);
				lines.setStrokeWidth(20);
				canvas.drawLine((int) (width * 0.2), (int) (height * 0.4), (int) (width * 0.8), (int) (height * 0.6),
						lines);
				canvas.drawLine((int) (width * 0.8), (int) (height * 0.4), (int) (width * 0.2), (int) (height * 0.6),
						lines);
			}

		}

		public void drawWhenTouched(Canvas canvas) {
			// countTries++; this should be moved to onFling :)

			Rect placeRed = new Rect();
			Rect placeGreen;
			Rect placeBlue;
			Rect randomline;
			Rect bottomColor;
			Paint graaay = new Paint();
			graaay.setColor(Color.MAGENTA);

			Rect back = new Rect(0, 0, width, height);
			if (colorInt != -1) {

				canvas.drawRect(back, myPaintYellow);
			}

			switch (colorInt) {
			case 0:

				painting = true;

				placeRed.set((int) (0.075 * width), thisY, (int) (0.325 * width), (int) (thisY + (0.2 * height)));
				canvas.drawRect(placeRed, myPaintRed);

				placeGreen = new Rect((int) (0.375 * width), (int) (0.1 * height), (int) (0.625 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeGreen, myPaintGreen);

				placeBlue = new Rect((int) (0.675 * width), (int) (0.1 * height), (int) (0.925 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeBlue, myPaintBlue);

				bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
				canvas.drawRect(bottomColor, myRandomPaint);// Should be
															// changing
															// color

				randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
				canvas.drawRect(randomline, graaay);

				// holder.unlockCanvasAndPost(canvas);

				break;
			case 1:
				painting = true;

				// canvas.drawColor(Color.YELLOW);

				placeRed.set((int) (0.075 * width), (int) (0.1 * height), (int) (0.325 * width), (int) (0.3 * height));
				canvas.drawRect(placeRed, myPaintRed);

				placeGreen = new Rect((int) (0.375 * width), thisY, (int) (0.625 * width),
						(int) (thisY + (0.2 * height)));

				canvas.drawRect(placeGreen, myPaintGreen);

				placeBlue = new Rect((int) (0.675 * width), (int) (0.1 * height), (int) (0.925 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeBlue, myPaintBlue);

				bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
				canvas.drawRect(bottomColor, myRandomPaint);// Should be
															// changing
															// color

				randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
				canvas.drawRect(randomline, graaay);

				// holder.unlockCanvasAndPost(canvas);
				break;
			case 2:
				painting = true;

				// canvas.drawColor(Color.YELLOW);

				placeRed.set((int) (0.075 * width), (int) (0.1 * height), (int) (0.325 * width), (int) (0.3 * height));
				canvas.drawRect(placeRed, myPaintRed);

				placeGreen = new Rect((int) (0.375 * width), (int) (0.1 * height), (int) (0.625 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeGreen, myPaintGreen);

				placeBlue = new Rect((int) (0.675 * width), thisY, (int) (0.925 * width),
						(int) (thisY + (0.2 * height)));
				canvas.drawRect(placeBlue, myPaintBlue);

				bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
				canvas.drawRect(bottomColor, myRandomPaint);// Should be
															// changing
															// color

				randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
				canvas.drawRect(randomline, graaay);

				// holder.unlockCanvasAndPost(canvas);
				break;
			}
			// painting = false;
			// itsaMatch();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Canvas canvas = holder.lockCanvas();
			if (holder.getSurface().isValid()) {
				// t.start();
				draw1(canvas);
				holder.unlockCanvasAndPost(canvas);
			} else {
				System.out.println("Can't draw! ");
			}
			startThread(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Canvas canvas = holder.lockCanvas();
			if (holder.getSurface().isValid() && (colorInt == 0 || colorInt == 1 || colorInt == 2)) {
				draw1(canvas);
				// drawWhenTouched(canvas);
				holder.unlockCanvasAndPost(canvas);
			} else {
				System.out.println("Can't draw! ");
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

		@Override
		public void run() {
			while (true) {
				if (painting || level == 2) {
					if (!holder.getSurface().isValid()) {
						continue;
					}
					dt = System.currentTimeMillis() - lastUpdate;
					if (dt >= skipTime) {
						if (level == 1) {
							if (thisY < (int) (0.55 * height) && colorInt != -1) {

								yesmatch = false;
								thisY += 10;
								Canvas canvas = view.holder.lockCanvas();
								drawWhenTouched(canvas);
								view.holder.unlockCanvasAndPost(canvas);
							} else if (thisY >= (int) (0.55 * height)) {
								matched = itsaMatch();
								myRandomPaint.setColor(generateColor());
								Canvas canvas = view.holder.lockCanvas();
								draw1(canvas);
								view.holder.unlockCanvasAndPost(canvas);
								painting = false;
							}
							lastUpdate = System.currentTimeMillis();
						} else if (level == 2) {
							//painting the bottom every 2 seconds
							
							if (System.currentTimeMillis() - update >= 2000) {
								if (!touched) {
									myRandomPaint.setColor(generateColor());
									Canvas canvas = view.holder.lockCanvas();
									draw1(canvas);
									view.holder.unlockCanvasAndPost(canvas);
									colorInt = -1;
								}
								update = System.currentTimeMillis();
							}
							if (thisY < (int) (0.55 * height) && colorInt != -1) {
								System.out.println("color int is : " + colorInt);
								yesmatch = false;
								thisY += 10;
								Canvas canvas = view.holder.lockCanvas();
								drawWhenTouched(canvas);
								view.holder.unlockCanvasAndPost(canvas);
							} else if (thisY >= (int) (0.55 * height) && colorInt !=-1) {
								matched = itsaMatch();
								myRandomPaint.setColor(generateColor());
								Canvas canvas = view.holder.lockCanvas();
								draw1(canvas);
								view.holder.unlockCanvasAndPost(canvas);
								painting = false;
								touched = false;
								colorInt = -1;
							}
							lastUpdate = System.currentTimeMillis();
						}
					}
				}
				if (countTries == 10) {
					countTries = 0;
					System.out.println("UI thread");
					GameActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							AlertDialog dialog = new AlertDialog.Builder(GameActivity.this)
									.setTitle("The Game is Done!")
									.setMessage("You answered " + (10 * winning) + "% correctly!")
									.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {
									finish();
								}
							}).create();
							dialog.show();

						}
					});

				}
			}
		}

		// There were so many random generations in your run method! So I
		// cleaned it a
		// little bit :D :D :D
		//
		// Canvas canvas = view.holder.lockCanvas();
		//// randnum = randnr.nextInt(100);
		//// if (randnum == 1) {
		//// myRandomPaint.setColor(generateColor());
		////
		//// }
		// drawWhenTouched(canvas);
		// view.holder.unlockCanvasAndPost(canvas);
		//
		// } else if (thisY >= (int) (0.55 * height)) {
		// matched = itsaMatch();
		// //thisY = (int) (0.1 * height);
		// Canvas canvas = view.holder.lockCanvas();
		// //randnum = randnr.nextInt(100);
		//// if (randnum == 1) {
		//// myRandomPaint.setColor(generateColor());
		////
		//// }
		// myRandomPaint.setColor(generateColor());
		// if (mynum == 1)
		// yesmatch = false;
		//
		// draw1(canvas);
		// if (matched) {
		// try {
		// Thread.sleep(4000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// view.holder.unlockCanvasAndPost(canvas);
		//
		// } else {
		// continue;
		// }
		//
		// }
		//
		// }
		// if (!holder.getSurface().isValid()) {
		// continue;
		// }
		//// randnum = randnr.nextInt(20);
		//// if (randnum == 1) {
		//// myRandomPaint.setColor(generateColor());
		////
		//// }
		// // if(countTries == 10){//Doesn't work
		// // new AlertDialog.Builder(this.getContext())
		// // .setTitle("Summary")
		// // .setMessage("Your average is.......?")
		// // .setPositiveButton(android.R.string.yes, new
		// // DialogInterface.OnClickListener() {
		// // public void onClick(DialogInterface dialog, int which) {
		// // // continue with delete
		// // finishActivity(0);
		// // }
		// // })
		// // .setNegativeButton(android.R.string.no, new
		// // DialogInterface.OnClickListener() {
		// // public void onClick(DialogInterface dialog, int which) {
		// // // do nothing
		// // }
		// // })
		// // .setIcon(android.R.drawable.dialog_frame)
		// // .show();
		// // }
		// // }
		// }

		public boolean itsaMatch() {
			boolean match = false;
			int nowCol = -1;
			Paint currPaint = new Paint();

			switch (colorInt) {
			case 0:
				nowCol = Color.RED;
				currPaint.setColor(nowCol);
				break;
			case 1:
				nowCol = Color.GREEN;
				currPaint.setColor(nowCol);
				break;
			case 2:
				nowCol = Color.BLUE;
				currPaint.setColor(nowCol);
				break;
			}
			if (thisY >= (int) (0.55 * height)) {// doesn't go in!!!!!
				if (currPaint.getColor() == myRandomPaint.getColor()) {
					// matched = true;
					match = true;
					winning++;
					yesmatch = true;
					drawCircle = true;
					drawLines = false;
					// if it's a match draw a circle!

				} else {// if it's not a match
					drawCircle = false;
					drawLines = true;
					System.out.println("WROOOOONG");
				}

			}
			countTries++;
			return match;
		}

		@Override
		public boolean performClick() {
			super.performClick();
			return true;
		}

	}

}