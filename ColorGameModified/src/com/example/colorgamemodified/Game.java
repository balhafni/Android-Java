package com.example.colorgamemodified;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;

public class Game extends Activity {
	MyRect recs;
	Bundle extras;
	public static String level;
	public static Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		extras = getIntent().getExtras();
		level = extras.getString("level");
		System.out.println("level yaay yaaay " + level);
		recs = new MyRect(this);
		setContentView(recs);
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

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			Log.v("gesture", "down down");
		} else if (action == MotionEvent.ACTION_MOVE)
			Log.v("gesture", "move move");
		return recs.getGesture(e);

	}

	public class MyRect extends SurfaceView implements SurfaceHolder.Callback, Runnable {
		String level;
		SurfaceHolder holder;
		boolean drawNow = true;
		Canvas canvas = null;
		boolean canDraw = false;
		Thread t = null;
		Paint paint, paintBottom, circle, xLines;
		Rect red, blue, green, finish, bottom, back, choosen;
		int width, height;
		GestureDetector gesture;
		int redChange, greenChange, blueChange;
		int choice = -1;
		int bottomColor = pickColorBottom();
		boolean redPicked, greenPicked, bluePicked;
		boolean isMoving = false;;
		boolean drawBottom = true;
		boolean drawX = false;
		float skipTime = 1000.0f / 30.0f; // 30 frames per second
		float dt;
		long lastUpdate;
		long lastChange;
		boolean drawCircle = false;
		int tempColor;
		AlertDialog dialog;
		boolean isPicked = false;
		int count = 10;
		int winning = 0;

		public MyRect(Context context) {
			super(context);
			holder = getHolder();
			holder.addCallback(this);
			width = MainActivity.width;
			height = MainActivity.height;
			gesture = new GestureDetector(context, gestureListener);
			redPicked = greenPicked = bluePicked = true;
			level = Game.level;

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (drawNow) {

				if (canDraw || level.equalsIgnoreCase("hard")) {
					if (!holder.getSurface().isValid()) {
						continue;
					}
					dt = System.currentTimeMillis() - lastUpdate;
					if (dt >= skipTime) {
						switch (choice) {
						case 0:
							redChange += 30;
							break;
						case 1:
							greenChange += 30;
							break;
						case 2:
							blueChange += 30;
							break;
						}

						if (level.equalsIgnoreCase("easy")) {
							if (redChange > height) {
								logic();
								redChange = 0;
								canDraw = false;
								isMoving = false;
								isPicked = false;
								bottomColor = pickColorBottom();
							}
							if (greenChange > height) {
								logic();
								greenChange = 0;
								canDraw = false;
								isPicked = false;
								isMoving = false;
								bottomColor = pickColorBottom();
								// drawBottom = true;
								choice = -1;
							}
							if (blueChange > height) {
								logic();
								blueChange = 0;
								canDraw = false;
								isPicked = false;
								isMoving = false;
								bottomColor = pickColorBottom();
							}
							Canvas canvas = holder.lockCanvas();
							drawRects(canvas);
							holder.unlockCanvasAndPost(canvas);
							lastUpdate = System.currentTimeMillis();
						} else if (level.equalsIgnoreCase("hard")) {
							canDraw = true;
							if (System.currentTimeMillis() - lastChange >= 2000) {
								if (!isMoving) {
									bottomColor = pickColorBottom();
								}
								lastChange = System.currentTimeMillis();
							}
							if (redChange > height) {
								logic();
								redChange = 0;
								// canDraw = false;
								bottomColor = pickColorBottom();
								choice = -1;
								isMoving = false;
								isPicked = false;
							}
							if (greenChange > height) {
								logic();
								greenChange = 0;
								// canDraw = false;
								bottomColor = pickColorBottom();
								choice = -1;
								isMoving = false;
								isPicked = false;
							}
							if (blueChange > height) {
								logic();
								blueChange = 0;
								bottomColor = pickColorBottom();
								choice = -1;
								isMoving = false;
								isPicked = false;
							}
							Canvas canvas = holder.lockCanvas();
							drawRects(canvas);
							holder.unlockCanvasAndPost(canvas);
							lastUpdate = System.currentTimeMillis();

						}

					}
				}
				if (count == 0) {
					count = 10;
					System.out.println("UI thread");
					Game.this.runOnUiThread(new Runnable() {
						public void run() {
							AlertDialog dialog = new AlertDialog.Builder(Game.this).setTitle("The Game is Done!").setMessage("You answered "+(count*winning)+"% correctly!")
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

		public boolean getGesture(MotionEvent e) {
			return gesture.onTouchEvent(e);
		}

		public void drawRects(Canvas c) {

			Paint paint = new Paint();
			back = new Rect(0, 0, width, height);
			paint.setColor(Color.BLACK);
			c.drawRect(back, paint);
			red = new Rect((int) (width * 0.15), (int) (redChange + height * 0.1), (int) (width * 0.30),
					(int) (redChange + height * 0.21));
			paint.setColor(Color.RED);
			c.drawRect(red, paint);
			paint.setColor(Color.GREEN);
			green = new Rect((int) (width * 0.45), (int) (greenChange + height * 0.1), (int) (width * 0.60),
					(int) (greenChange + height * 0.21));
			c.drawRect(green, paint);
			Rect blue = new Rect((int) (width * 0.75), (int) (blueChange + height * 0.1), (int) (width * 0.90),
					(int) (blueChange + height * 0.21));
			paint.setColor(Color.BLUE);

			c.drawRect(blue, paint);
			// drawing the finish line
			finish = new Rect(0, (int) (height * 0.7), (int) (width), (int) (height * 0.73));
			paint.setColor(Color.GRAY);

			c.drawRect(finish, paint);
			// drawing the bottom rectangle
			paintBottom = new Paint();
			bottom = new Rect(0, (int) (height * 0.78), (int) (width), (int) (height));
			paintBottom.setColor(bottomColor);
			c.drawRect(bottom, paintBottom);
			// drawing a circle
			if (drawCircle) {
				circle = new Paint();
				circle.setStyle(Paint.Style.STROKE);
				circle.setColor(Color.WHITE);
				circle.setStrokeWidth(20);
				c.drawCircle((int) (width * 0.5), (int) (height * 0.5), 400, circle);
				// drawCircle = false;
			}
			if (drawX) {
				xLines = new Paint();
				xLines.setColor(Color.RED);
				xLines.setStyle(Paint.Style.STROKE);
				xLines.setStrokeWidth(20);
				c.drawLine((int) (width * 0.2), (int) (height * 0.4), (int) (width * 0.8), (int) (height * 0.6),
						xLines);
				c.drawLine((int) (width * 0.8), (int) (height * 0.4), (int) (width * 0.2), (int) (height * 0.6),
						xLines);
				// drawX = false;
			}

		}

		public int pickColorBottom() {
			Random rand = new Random();
			int pick = rand.nextInt(3);
			switch (pick) {
			case 0:
				return Color.RED;
			case 1:
				return Color.GREEN;
			case 2:
				return Color.BLUE;
			}
			return -1;
		}

		private void tryDrawing(SurfaceHolder holder) {
			Canvas canvas = holder.lockCanvas();
			if (canvas == null) {
			} else {
				drawRects(canvas);
				holder.unlockCanvasAndPost(canvas);
			}
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			tryDrawing(holder);
			newGame(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO Auto-generated method stub

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

		}

		public void newGame(SurfaceHolder holder) {
			t = new Thread(this);
			t.start();

		}

		SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
			@Override
			public boolean onDoubleTap(MotionEvent event) {
				Log.v("gesture", "double tap");
				return true;
			}

			@Override
			public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
				if (!isPicked) {
					int x = (int) event1.getX();
					int y = (int) event1.getY();
					System.out.println("x is: " + x);
					Log.v("motion", x + "");
					System.out.println("y is: " + y);
					System.out.println("bla : " + width * 0.15);
					System.out.println("blabla : " + width * 0.45);
					System.out.println("The level is " + level);
					System.out.println("The count is " + count);
					choice = getPicked(x, y);
					System.out.println(canDraw);
				}
				return true;
			}
		};

		public void setRec(Rect rec) {
			this.choosen = rec;
		}

		public int getPicked(float x, float y) {
			if (x >= (int) (width * 0.15) && x <= (int) (width * 0.30)) {
				System.out.println("I'm red");

				canDraw = true;
				isMoving = true;
				tempColor = Color.RED;
				// count--;
				isPicked = true;
				return 0;
			} else if (x >= (int) (width * 0.45) && x <= (int) (width * 0.60)) {
				System.out.println("I'm green");
				canDraw = true;
				isMoving = true;
				tempColor = Color.GREEN;
				isPicked = true;
				// count--;
				return 1;
			} else if (x >= (int) (width * 0.75) && x <= (int) (width * 0.90)) {
				System.out.println("I'm blue");
				canDraw = true;
				isMoving = true;
				tempColor = Color.BLUE;
				isPicked = true;
				// count--;
				return 2;
			}

			return -1;
		}

		public void logic() {
			if (tempColor == bottomColor) {
				drawCircle = true;
				drawX = false;
				winning++;
				System.out.println("Trueeeeee");
			} else {
				drawX = true;
				drawCircle = false;
				System.out.println("Wrooooooong");
			}
			count--;
		}
	}

}