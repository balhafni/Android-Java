package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	Context context = this;

	Animation rotate;
	Button goToProfile, createAProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logo logo = new Logo(context, 300, 500);
		setContentView(logo);

		rotate = AnimationUtils.loadAnimation(this, R.animator.rotate);
		rotate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setContentView(R.layout.activity_main);
				// initiazling elements
				goToProfile = (Button) findViewById(R.id.takePicBtn);
				createAProfile = (Button) findViewById(R.id.button2);
				createAProfile.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
						startActivityForResult(intent, 0);
					}
				});
				goToProfile.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						checkForProfile();
						
					}
				});
			}
		});
		logo.startAnimation(rotate);

	}

	public void checkForProfile() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Hello User");
		alert.setMessage("What is your ID:");

		// Use an EditText view to get user input.
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(MainActivity.this,AcademicActivity.class);
				startActivityForResult(intent, 0);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});

		alert.show();
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
