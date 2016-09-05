package com.example.shooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	SurfaceHolder holder;
	int screenWidth;
	int screenHeight;
	Rect block;
	Paint blockPaint;
	Thread t = null;
	int x, y;// location of the block;
	int block_width = 100;
	int block_height = 100;
	int change = 0;
	boolean drawNow = true;
	Canvas canvas = null;
	boolean canDraw = false;

	public MainView(Context context) {
		super(context);
		Log.v("surface", "surface MainView_ 1");
		holder = getHolder();
		holder.addCallback(this);// super important!!! needed to get the changes
		block = new Rect();
		blockPaint = new Paint();
		blockPaint.setColor(Color.BLUE);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v("surface", "surface created_3");

		newGame(holder);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.v("surface", "surface size changed_2" + w + "," + h + "," + oldw + "," + oldh);
		screenHeight = h;
		screenWidth = w;

	}

	public void newGame(SurfaceHolder holder) {

		t = new Thread(this);
		t.start();
		canDraw = true;
	}

	public void drawCanvas(Canvas c) {
		// change the background
		Paint backColor = new Paint();
		backColor.setColor(Color.RED);
		c.drawRect(0, 0, c.getWidth(), c.getHeight(), backColor);
		block.set(x, y + change, block_width, block_height + change);
		c.drawRect(block, blockPaint);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v("surface", "surface changed_4");

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("surface", "surface destroyed_5");

	}

	@Override
	public void run() {
		while (drawNow) {
			if (canDraw) {
				if (!holder.getSurface().isValid()) {
					continue;
				}
				change += 10;
				if (change > screenHeight)
					change = 0;
				Canvas canvas = holder.lockCanvas();
				Log.v("size", canvas.getHeight() + "");
				drawCanvas(canvas);
				holder.unlockCanvasAndPost(canvas);
			}

		}

	}

}
