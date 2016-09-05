package com.example.empsdatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "Employee.db";

	public static final String TABLE_NAME = "Emp_Table";

	public static final String COLUMN_1 = "ID";

	public static final String COLUMN_2 = "First_Name";

	public static final String COLUMN_3 = "Last_Name";

	public static final String COLUMN_4 = "StartDate";

	public static final String COLUMN_5 = "Favorite_Food";

	public static final String COLUMN_6 = "Favorite_Game1";

	public static final String COLUMN_7 = "Favorite_Game2";

	public static final String COLUMN_8 = "Favorite_Color";

	public static final String COLUMN_9 = "Gender";

	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME
				+ " (First_Name TEXT,Last_Name TEXT,StartDate TEXT,Favorite_Food TEXT,Favorite_Game1 TEXT,Favorite_Game2 TEXT, Favorite_Color Text,Gender TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public boolean insertData(String fname, String lname, String date, String favFood, String favGame1,
			String faveGame2, String favColor, String Gender) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_2, fname);
		values.put(COLUMN_3, lname);
		values.put(COLUMN_4, date);
		values.put(COLUMN_5, favFood);
		values.put(COLUMN_6, favGame1);
		values.put(COLUMN_7, faveGame2);
		values.put(COLUMN_8, favColor);
		values.put(COLUMN_9, Gender);
		long check = db.insert(TABLE_NAME, null, values);
		if (check == -1) {
			return false;
		} else {
			return true;
		}
	}

	public Cursor getAllData() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
		return res;
	}

	public void deleteDataBase() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + TABLE_NAME);
	}

	public Cursor getEmpsWhoLikeTetris() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery(
				"select * from " + TABLE_NAME + " where Favorite_Game2 = 'tetris' OR Favorite_Game1 = 'tetris'", null);
		return res;
	}

	public Cursor getFemalesWhoLikePacMan() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME
				+ " where (Favorite_Game2 = 'pacman' OR Favorite_Game1 = 'pacman') AND Gender='female'", null);
		return res;
	}

	public Cursor getEmpsHiredAfterTwoTh() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where StartDate > ('2000-01-01')", null);
		return res;
	}

	public ArrayList<Employee> gettingListsWhoLikeLOLCODandMC() {
		SQLiteDatabase db = this.getWritableDatabase();

		ArrayList<Employee> list1 = new ArrayList<Employee>();
		ArrayList<Employee> list2 = new ArrayList<Employee>();
		// getting all the emps who like LOL
		Cursor res = db.rawQuery("select * from " + TABLE_NAME
				+ " where Favorite_Game1 = 'League_of_Legs' OR Favorite_Game2 = 'League_of_Legs'", null);

		while (res.moveToNext()) {
			list1.add(new Employee(res.getString(0), res.getString(1), res.getString(2), res.getString(3),
					res.getString(4), res.getString(5), res.getString(6), res.getString(7)));
		}
		// getting all the emps who like COD
		Cursor res2 = db.rawQuery("select * from " + TABLE_NAME
				+ " where (Favorite_Game1 = 'call_of_duty' OR Favorite_Game2 = 'call_of_duty')", null);
		res2.moveToFirst();
		while (res2.moveToNext()) {
			list1.add(new Employee(res2.getString(0), res2.getString(1), res2.getString(2), res2.getString(3),
					res2.getString(4), res2.getString(5), res2.getString(6), res2.getString(7)));
		}

		// getting the final list
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).getFavGame2().equals("mortal_kombat")) {
				list2.add(list1.get(i));
			}
			
		}
		for (int i = 0; i < list2.size(); i++) {
			System.out.println(list2.toString());
		}
		return list2;
	}

	public Cursor getEmpsNotMaleNorFemaleLikeTetris() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("select Favorite_Color from " + TABLE_NAME
				+ " where (Gender = 'Drell' OR Gender = 'Quarian' OR Gender = 'Asari') AND (Favorite_Game1 = 'tetris' OR Favorite_Game2 = 'tetris')"
				+ " ORDER BY Favorite_Color ASC", null);
		return res;
	}

}
