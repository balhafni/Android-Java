package com.example.colorstouchgame;

import java.util.Random;

import com.example.colorstouchgame.GameActivity.MyView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Scene;
import android.transition.Transition;
import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;


public class ChooseModeClass extends Activity {
	Paint myPaintRed, myPaintGreen, myPaintBlue, myPaintYellow, myRandomPaint;
	MyView view;
	float x, y;
	int width, height, colorInt;
	boolean startThread = true;
	boolean painting = false;
	boolean matched = false, yesmatch = false;
	GestureDetector gesture;
	Random randnr;
	int randnum, mynum;
	Handler handler;
	int level = 1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
	}
	public class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
		SurfaceHolder holder;
		Boolean change = false;
		Thread t = null;
		int thisY;
		
		public MyView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
	
}}
