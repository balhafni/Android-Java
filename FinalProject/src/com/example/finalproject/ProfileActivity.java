package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	String id, firstName, lastName, dateOfBirth, major;
	EditText text_id, text_firstName, text_lastName, text_dateOfBirth;
	TextView text_major;
	Button pickMajor;
	Button goToClasses, createProfile, clearDataBase, takePicture, checkData;
	boolean inserted;
	boolean[] check;
	boolean profileCreated = false;
	CharSequence[] majors = { "Computer_Science", "Math" };
	ImageView profilePic;
	Vibrator vibrate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_layout);
		// initializing elements
		text_id = (EditText) findViewById(R.id.ID);
		text_firstName = (EditText) findViewById(R.id.firstName);
		text_lastName = (EditText) findViewById(R.id.lastName);
		text_dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
		text_major = (TextView) findViewById(R.id.major);
		pickMajor = (Button) findViewById(R.id.pickMajor);
		goToClasses = (Button) findViewById(R.id.goToClasses);
		createProfile = (Button) findViewById(R.id.createProfile);
		clearDataBase = (Button) findViewById(R.id.clearDataBase);
		takePicture = (Button) findViewById(R.id.takePicBTN);
		profilePic = (ImageView) findViewById(R.id.profilePic);
		profilePic.setAlpha(0f);
		vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		// to choose the major
		pickMajor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				chooseMajor();

			}
		});

		// to insert the data in the profile database
		createProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				vibrate.vibrate(200);
				if (checkForInput()) {
					boolean check = false;
					profilePic.buildDrawingCache();
					Bitmap bitmap = ((Bitmap) profilePic.getDrawingCache());

					if (bitmap != null) {
						check = MainActivity.profilesDataBase.insertData(text_id.getText().toString(),
								text_firstName.getText().toString(), text_lastName.getText().toString(),
								text_dateOfBirth.getText().toString(), text_major.getText().toString(), "", bitmap);
					}
					if (check) {
						profileCreated = true;
						Toast.makeText(getApplicationContext(), "Your profile has been created", Toast.LENGTH_LONG)
								.show();
					} else if (check == false) {
						Toast.makeText(getApplicationContext(), "Your profile has NOT been created", Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		});
		// to clear database
		clearDataBase.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.profilesDataBase.deleteDataBase();
				Toast.makeText(getApplicationContext(), "Database was cleared!", Toast.LENGTH_LONG).show();

			}
		});
		// to go to the academicActivity
		goToClasses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				vibrate.vibrate(200);
				if (profileCreated) {
					Intent intent = new Intent(ProfileActivity.this, AcademicActivity.class);
					intent.putExtra("id", text_id.getText().toString());
					startActivityForResult(intent, 0);
				} else {
					showMessage("Oopss!!", "Your profile hasn't been created yet!");
				}
			}
		});
		// go to the camera activity
		takePicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				vibrate.vibrate(200);
				Intent intentCamera = new Intent(ProfileActivity.this, CameraMain.class);
				startActivityForResult(intentCamera, 1);
			}
		});

	}

	public String getAllData() {
		Cursor res = MainActivity.profilesDataBase.getAll();
		if (res.getCount() == 0) {
			Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT);
		}
		StringBuilder builder = new StringBuilder();
		while (res.moveToNext()) {
			builder.append("First Name: " + res.getString(0) + "\n").append("Last Name: " + res.getString(1) + "\n")
					.append("Start Date: " + res.getString(2) + "\n").append("Fav Food: " + res.getString(3) + "\n")
					.append("Fav Game1: " + res.getString(4) + "\n").append("Fav Game2: " + res.getString(5) + "\n")
					.append("Fav Color: " + res.getString(6) + "\n").append("Gender: " + res.getString(7) + "\n\n");
		}
		res.close();
		return builder.toString();
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

	public void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}

	public boolean checkForInput() {
		if (text_id.getText().toString().matches("")) {
			showMessage("Oops!", "You haven't entered your ID!");
			return false;
		}
		if (text_firstName.getText().toString().matches("")) {
			showMessage("Oops!", "You haven't entered your first name!");
			return false;
		}
		if (text_lastName.getText().toString().matches("")) {
			showMessage("Oops!", "You haven't entered your last name!");
			return false;
		}
		if (text_major.getText().toString().matches("")) {
			showMessage("Oops!", "You haven't entered your major!");
			return false;
		}
		if (text_dateOfBirth.getText().toString().matches("")) {
			showMessage("Oops!", "You haven't entered your date of birth!");
			return false;
		}
		if (profilePic.getAlpha() == 0) {
			showMessage("Oops!", "You haven't taken a picture!");
			return false;
		}
		return true;
	}

	public void chooseMajor() {
		AlertDialog.Builder areaOptions = new AlertDialog.Builder(ProfileActivity.this);
		areaOptions.setTitle("Pick a Major");
		check = new boolean[majors.length];

		for (int i = 0; i < check.length; i++) {
			check[i] = false;
		}

		areaOptions.setSingleChoiceItems(majors, -1, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				text_major.setText(majors[which]);
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
