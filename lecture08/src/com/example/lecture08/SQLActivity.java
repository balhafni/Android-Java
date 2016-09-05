package com.example.lecture08;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SQLActivity extends Activity{
SQL db;
Button writebtn,readbtn;
TextView seeTxt;
SQLiteDatabase qdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sql_activity);
		
		writebtn = (Button)findViewById(R.id.writeBTN);
		writebtn.setOnClickListener(clickme);
		readbtn = (Button)findViewById(R.id.readBTN);
		readbtn.setOnClickListener(clickme);
		seeTxt  =(TextView)findViewById(R.id.DBtxt);
		db = new SQL(this);//,null,null,1);
		qdb = db.getReadableDatabase();
		//1 table name
		//2 array of column names to return, null returns all columns
		//3 the WHERE clause, null all data, do not use the word where i.e._id=19 and summary=?"
		//4 selection arguments, have ? in the WHERE and the placeholder gets replace with these
		//5 GROUP by clause, null means rows wont be grouped
		//6 HAVING clause, null means no filter
		//7 ORDER BY clause, null no ordering
		//Cursor recordset1 = qdb.query("employees", null, null, null, null, null, null);
		
		//1 sql query
		//2 selection argument, having ? in the query replace them with strings
		
		//Cursor recordset2 = qdb.rawQuery("SELECT * FROM employees", null);
		//Cursor recordset3 = qdb.rawQuery("select * from todo where _id = ?", new String[] { id });
	}
	
	OnClickListener clickme = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.readBTN:
			Cursor recordset1 = qdb.query("employees", null, null, null, null, null, null);
			Cursor recordset2 = qdb.rawQuery("SELECT * FROM employees", null);
			StringBuilder theRow = new StringBuilder();
			if (recordset2.getCount()>0)
			{
			recordset2.moveToFirst();	
			theRow.append(recordset2.getString(0));
			theRow.append(recordset2.getString(1));
			theRow.append(recordset2.getString(2));
			theRow.append(recordset2.getString(3));
			theRow.append(recordset2.getString(4));
			seeTxt.setText("row info: "+theRow.toString());
		
			}
			else
				seeTxt.setText("no rows in the cursor");
			break;
			
		case R.id.writeBTN:
			
			 ContentValues values = new ContentValues();
			 values.put("name", "carlos");
			 values.put("ext", "123");
			 values.put("mob","mobi");
			 values.put("age",23);
			 //returns row id or -1 if error
			 long ins = qdb.insert("employees", null, values);
			 if (ins > 0)
				 seeTxt.setText("row inserted, row# "+ins);
			 else
				 seeTxt.setText("row not insertd");
			break;
		}
			
		}
	};
	
	
}
