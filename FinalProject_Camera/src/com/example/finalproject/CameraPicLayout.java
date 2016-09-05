package com.example.finalproject;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPicLayout extends SurfaceView implements SurfaceHolder.Callback{
	 private Camera camera;
	 private SurfaceHolder holder;
	 
	 public CameraPicLayout(Context context,Camera cam,int degrees) {
			super(context);
			camera = cam;
			camera.setDisplayOrientation(degrees);
			holder = getHolder();
			holder.addCallback(this);//what is the purpose of this ????
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			 try   {
				 
		         camera.setPreviewDisplay(holder);//for live preview
		         camera.startPreview(); //start the previews
		      } catch (IOException e) {
		      }
			
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
}
