package com.example.list_09c;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	Button addBtn, clearBtn; // reference button to add to list, clear the list
	ListView guiList; // reference ListView
	List<String> nameList; // all the names that will be in the list
	ArrayAdapter<String> adapter;// adapter for the ListView
	int counter = -1; // counter for the position of the names
	String names[]; // names to add
	TextView total; // show total amount

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addBtn = (Button) findViewById(R.id.button1);
		clearBtn = (Button) findViewById(R.id.clearBtn);
		guiList = (ListView) findViewById(R.id.listView1);
		total = (TextView) findViewById(R.id.textView2);
		nameList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, nameList);
		guiList.setAdapter(adapter);// set the data that will be in the list
		guiList.setOnItemClickListener(itemListener);
		names = new String[] { "john", "peter", "bob", "sue", "hammer" };
		addBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addName();

			}
		});
		clearBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				adapter.clear(); // remove them all
				adapter.notifyDataSetChanged(); // refreshes the gui listView
				changeTotal();
			}
		});
	}

	public void addName() {
		counter++;
		nameList.add(names[counter]);
		adapter.notifyDataSetChanged(); // refreshes the gui listView
		if (counter == 4)
			counter = 0;
		changeTotal();
	}

	private void changeTotal() {
		total.setText("Total:" + adapter.getCount());
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			nameList.remove(position); // remove the clicked name from the list
			adapter.notifyDataSetChanged(); // refreshes the gui listView
			changeTotal(); // change the total text
		}
	};

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
