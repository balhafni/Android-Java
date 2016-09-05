package com.example.fullSoundVibrateThreadplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FullScreenActivity extends Activity {
int who =0;
Button btn, btn2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View decorView = getWindow().getDecorView();
		//hide all three ( status bar (the time), system bar( title and logo) and navigation bar( bottom buttons)
		//swiping down from the top of the screen will show the status and navigation temporarily
		 //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
	      //          					|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN);
		 
		//hide just the status bar and the navigation bar
		//swiping down from the top of the screen will show the status and navigation temporarily
		//hitting a menu action (like volume or a menu option will reset the immerse mode!(decorView must be set again)
		// decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
			//		  					  |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN);
		 
		//hides just the status bar, it will come back when user swipes or pressed a menu action
		 //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		 
		//hides just the navigation bar, it will come back when user swipes or pressed a menu action
		// decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		 setContentView(R.layout.activity_main);
		 btn2 = (Button)findViewById(R.id.button2);
		//btn2.setVisibility(View.GONE);
		 btn = (Button)findViewById(R.id.button1);
		 btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (who == 0)
				{
					 decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
					 btn.setText("hide navigation");
					who = 1;
				}
				else if (who == 1)
				{
					decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
					btn.setText("hide the status bar");
					who = 2;
				}
				else if (who == 2)
				{
					decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
								  					  |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN);
					btn.setText("hide the status and the navigation");
					who = 3;
				}
				else if (who == 3)
				{
					decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						               					|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN);
					btn.setText("hide it all");
					who = 0;
				}
				
				
			}
		});
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
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
		//	return true;
		//}
		Intent toVibrations = new Intent(FullScreenActivity.this,Vibrationss.class);
		startActivity(toVibrations);
		return super.onOptionsItemSelected(item);
	}
	
	public void gotoGame(View v)
	{
		Intent tent = new Intent(FullScreenActivity.this,Main.class);
		startActivity(tent);
	}
}
