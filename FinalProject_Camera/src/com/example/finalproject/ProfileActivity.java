package com.example.finalproject;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	String id, firstName, lastName, dateOfBirth, major;
	EditText text_id, text_firstName, text_lastName, text_dateOfBirth, text_major;
	Button goToClasses, createProfile, takePicture;
	boolean inserted;
	boolean[] check;
	ProfileDataBase profileDB;
	CharSequence[] majors = { "Computer_Science", "Math" };
	ImageView profilePic;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_layout);
		//initializing elements
	
		text_id = (EditText) findViewById(R.id.ID);
		text_firstName = (EditText) findViewById(R.id.firstName);
		text_lastName = (EditText) findViewById(R.id.lastName);
		text_dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
		text_major = (EditText) findViewById(R.id.Major);
		goToClasses = (Button) findViewById(R.id.goToClasses);
		createProfile = (Button) findViewById(R.id.createProfile);
		takePicture = (Button) findViewById(R.id.takePicBtn);
		profilePic = (ImageView) findViewById(R.id.profilePic);
	profilePic.setAlpha(0f);
		
		// to choose the major
		text_major.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseMajor();

			}
		});
		// to insert the data in the profile database
		createProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean check = profileDB.insertData(text_id.getText().toString(), text_firstName.getText().toString(),
						text_lastName.getText().toString(), text_dateOfBirth.getText().toString(),
						text_major.getText().toString());
				if (check) {
					Toast.makeText(getApplicationContext(), "Your profile has been created", Toast.LENGTH_LONG).show();
				}
			}
		});

		// to go to the academicActivity
		goToClasses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileActivity.this, AcademicActivity.class);
				startActivityForResult(intent, 0);
			
				
				
			}
		});
		
		//go to the camera activity
		takePicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentCamera = new Intent(ProfileActivity.this, CameraMain.class);
				startActivityForResult(intentCamera, 1);
				
				
		//		profilePic.setBackground(CameraMain.img.getDrawable());
		//		profilePic.setVisibility(profilePic.VISIBLE);
				
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == 1) {   
			  Drawable d = CameraMain.img.getDrawable();

			  profilePic.setImageDrawable(d);
				profilePic.setAlpha(1f);
				takePicture.setEnabled(false);
				takePicture.setVisibility(takePicture.INVISIBLE);

		  }
		}
	public void chooseMajor() {
		AlertDialog.Builder areaOptions = new AlertDialog.Builder(ProfileActivity.this);
		areaOptions.setTitle("Pick a Major");
		check = new boolean[majors.length];

		for (int i = 0; i < check.length; i++) {
			check[i] = false;
		}

		areaOptions.setMultiChoiceItems(majors, check, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					check[which] = true;
				}
			}
		});

		areaOptions.setPositiveButton("Select", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = 0; i < check.length; i++) {
					if (check[i]) {
						text_major.setText(majors[i]);
					}
				}

			}
		});
		areaOptions.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
			}

		});
		areaOptions.show();
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
