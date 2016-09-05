package com.example.relativelayout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {
	Button btn;
	Spinner spinner1;
	List <String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button) findViewById(R.id.button1);
		spinner1 = (Spinner) findViewById(R.id.spinner1); 
		spinner1.setPrompt("Choose a Fruit");
		list = new ArrayList<String>();
		list.add("Tomato"); list.add("lettuce"); list.add("onions"); list.add("olives");
		btn.setText("Want viggies?");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, list);
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(spinnerAdapter);
			}
		});
	
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch(position){
				case 0:
					btn.setText("You picked the 1st item");
					break;
				case 1:
					btn.setText("You picked the 2nd item");
					break;
				case 2:
					btn.setText("You picked the 3rd item");
					break;
				case 3:
					btn.setText("You picked the 4th item");
					break;
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
