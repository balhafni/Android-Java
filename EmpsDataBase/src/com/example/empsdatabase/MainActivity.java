package com.example.empsdatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	DataBase myDataBase;
	Button btn, view, delete, tetris, colorTest;
	String fname, lname, date, favFood, favGame1, favGame2, favColor, gender;
	String formattedDate;
	ListView listView;
	ArrayList<String> queries;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myDataBase = new DataBase(this);
		btn = (Button) findViewById(R.id.button1);
		delete = (Button) findViewById(R.id.button2);
		listView = (ListView) findViewById(R.id.listView1);
		// tetris = (Button) findViewById(R.id.button4);
		// colorTest = (Button) findViewById(R.id.button5);
		queries = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, queries);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemListener);
		// adding the list items to the list
		queries.add("Get All Data");
		queries.add("Get Emps who like tetris");
		queries.add("Get Emps who are Female and Like Pacman");
		queries.add("Get Emps who were hired after year 2000");
		queries.add("Get Emps who like LOL and COD and Mortal Combat");
		queries.add("Get the fav color of emp who aren't males nor females and like tetris");

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					read();
					Toast.makeText(getApplicationContext(), "Data was inserted", Toast.LENGTH_LONG).show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clearDataBase();
				Toast.makeText(getApplicationContext(), "Data was removed", Toast.LENGTH_LONG).show();

			}
		});
	}

	public void read() throws IOException {

		InputStream inputStream = null;
		StringBuffer buff = new StringBuffer();
		String str = "";
		String[] temp;
		inputStream = this.getResources().getAssets().open("info.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		if (inputStream != null) {
			while ((str = reader.readLine()) != null) {
				temp = str.split(" ");
				fname = temp[0];
				lname = temp[1];
				date = temp[2];
				formattedDate = changeDateFormat(date);
				favFood = temp[3];
				favGame1 = temp[4];
				favGame2 = temp[5];
				favColor = temp[6];
				gender = temp[7];
				myDataBase.insertData(fname, lname, formattedDate, favFood, favGame1, favGame2, favColor, gender);
				System.out.println(str);
				buff.append(str + "\n");
			}
			inputStream.close();
		}
	}

	public void clearDataBase() {
		myDataBase.deleteDataBase();
	}

	public void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case 0:
				queries.set(0, getAllData());
				break;
			case 1:
				queries.set(1, empsWhoLikeTetris());
				break;
			case 2:
				queries.set(2, femalesWhoLikePacMan());
				break;
			case 3:
				queries.set(3, empsHiredAfterTwoTh());
				break;
			case 4:
				queries.set(4, empsWhoLikeLOLCODandMC());
				break;
			case 5:
				queries.set(5, empsNotMaleNorFemaleLikeTetris());
				break;
			}
			adapter.notifyDataSetChanged();
		}
	};

	public String getAllData() {
		Cursor res = myDataBase.getAllData();
		if (res.getCount() == 0) {
			showMessage("ERROR", "No data!");
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

	public String empsWhoLikeTetris() {
		Cursor res = myDataBase.getEmpsWhoLikeTetris();
		if (res.getCount() == 0) {
			showMessage("ERROR", "No data!");
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

	public String femalesWhoLikePacMan() {
		Cursor res = myDataBase.getFemalesWhoLikePacMan();
		if (res.getCount() == 0) {
			showMessage("ERROR", "No data!");
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

	public String empsHiredAfterTwoTh() {
		Cursor res = myDataBase.getEmpsHiredAfterTwoTh();
		if (res.getCount() == 0) {
			showMessage("ERROR", "No data!");
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

	public String empsWhoLikeLOLCODandMC() {
		ArrayList<Employee> list = myDataBase.gettingListsWhoLikeLOLCODandMC();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			builder.append("First Name: " + list.get(i).getfName() + "\n")
					.append("Last Name: " + list.get(i).getlName() + "\n")
					.append("Start Date: " + list.get(i).getStartDate() + "\n")
					.append("Fav Food: " + list.get(i).getFavFood() + "\n")
					.append("Fav Game1: " + list.get(i).getFavGame1() + "\n")
					.append("Fav Game2: " + list.get(i).getFavGame2() + "\n")
					.append("Fav Color: " + list.get(i).getFavColor() + "\n")
					.append("Gender: " + list.get(i).getGender() + "\n\n");

		}

		return builder.toString();
	}

	public String empsNotMaleNorFemaleLikeTetris() {
		Cursor res = myDataBase.getEmpsNotMaleNorFemaleLikeTetris();
		if (res.getCount() == 0) {
			showMessage("ERROR", "No data!");
		}
		StringBuilder builder = new StringBuilder();
		while (res.moveToNext()) {
			builder.append("Fav Color: " + res.getString(0) + "\n\n");
		}
		return builder.toString();
	}

	// this method is to change the given date format so that it matches the
	// sqlite format
	public String changeDateFormat(String date) {
		String temp1, temp2, dateFormatted;
		String str = date.replaceAll("/", "-");
		String[] parsed = str.split("-");
		temp1 = parsed[0];
		temp2 = parsed[1];
		// swapping
		parsed[0] = parsed[2];
		parsed[1] = temp1;
		parsed[2] = temp2;
		return dateFormatted = parsed[0] + "-" + parsed[1] + "-" + parsed[2];
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
