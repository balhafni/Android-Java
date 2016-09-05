package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileDataBase extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "StudentProfiles.db";
	public static final String TABLE_NAME = "Profiles";
	public static final String ID = "ID";
	public static final String FIRST_NAME = "First_Name";
	public static final String LAST_NAME = "Last_Name";
	public static final String DATE_OF_BIRTH = "Date_of_Birth";
	public static final String MAJOR = "Major";

	public ProfileDataBase(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME
				+ " (ID TEXT,First_Name TEXT,Last_Name TEXT,Date_of_Birth TEXT,Major TEXT);");

	}

	public boolean insertData(String id, String firstName, String lastName, String dateOfBirth, String major) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID, id);
		values.put(FIRST_NAME, firstName);
		values.put(LAST_NAME, lastName);
		values.put(DATE_OF_BIRTH, dateOfBirth);
		values.put(MAJOR, major);
		long check = db.insert(TABLE_NAME, null, values);
		if (check == -1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);

	}

}
