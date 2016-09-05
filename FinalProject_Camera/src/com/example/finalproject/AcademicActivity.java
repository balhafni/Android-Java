package com.example.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class AcademicActivity  extends Activity implements OnItemClickListener{
	String className, classTime, major;
	int credits, priority;
	AcademicDataBase dataBase;
	Button btn, btn2,drop;
	ArrayList<AcedemicPlans> list;
	ArrayList<String> classesTaken;
	String[] classesNames;
	ListView listViewTaken;
	boolean[] check;
	ArrayAdapter<String> adapterTaken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBase = new AcademicDataBase(this);
		setContentView(R.layout.academic_layout);

		btn = (Button) findViewById(R.id.takePicBtn);
		btn2 = (Button) findViewById(R.id.button2);
		drop = (Button) findViewById(R.id.button3);
		drop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			dataBase.dropTable();	
				
			}
		});
		listViewTaken = (ListView) findViewById(R.id.listView1);
		list = new ArrayList<AcedemicPlans>();
		classesTaken = new ArrayList<String>();
		classesTaken.add("Classes Taken");

		adapterTaken = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1,
				classesTaken);

		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					read();
					Toast.makeText(getApplicationContext(), "Data was inserted", Toast.LENGTH_LONG).show();
					classesNames = new String[list.size()];
					for (int i = 0; i < classesNames.length; i++) {
						classesNames[i] = list.get(i).getClassName();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		listViewTaken.setAdapter(adapterTaken);
		listViewTaken.setOnItemClickListener(this);
		setClickLis(btn);

	}

	public void setClickLis(Button button) {
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder areaOptions = new AlertDialog.Builder(AcademicActivity.this);
				areaOptions.setTitle("Pick Classes");

				check = new boolean[classesNames.length];

				for (int i = 0; i < check.length; i++) {
					check[i] = false;
				}

				areaOptions.setMultiChoiceItems(classesNames, check, new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) {
							check[which] = true;
						}

					}
				});

				areaOptions.setPositiveButton("Start", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						for (int i = 0; i < check.length; i++) {
							if (check[i] == true) {
								if (!classesTaken.contains(classesNames[i]))
									classesTaken.add(classesNames[i].toString());

							}

						}
						adapterTaken.notifyDataSetChanged();
					}
				});
				areaOptions.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
					}

				});
				areaOptions.show();
			}
		});
	}
	
	
	
	public void read() throws IOException {
		InputStream inputStream = null;
		StringBuffer buff = new StringBuffer();
		String str = "";
		String[] temp;
		inputStream = this.getResources().getAssets().open("AcademicInfo.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		if (inputStream != null) {
			while ((str =reader.readLine()) != null) {
				//str = reader.readLine();
				if (!str.contains(" ")) {
					major = str;
					str = reader.readLine();
				}
				System.out.println("INSERTEEEED");
				temp = str.split(" ");
				className = temp[0];
				classTime = temp[1];
				credits = Integer.parseInt(temp[2]);
				priority = Integer.parseInt(temp[3]);
				list.add(new AcedemicPlans(className, classTime, major, credits, priority));
				dataBase.insertData(major, className, classTime, credits, priority);
				System.out.println(str);
				buff.append(str + "\n");
			}
			inputStream.close();
		}
	}

	public void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}
}
