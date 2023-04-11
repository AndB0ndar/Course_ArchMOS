package com.example.coursearchmos.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBAdapter {
//	public static final String DATABASE_NAME = "reader.db";
	public static final String DATABASE_NAME = "note.db";
	public static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_CARS =
			"CREATE TABLE " + NoteDBAdapter.NOTE_TABLE
					+ " (" + NoteDBAdapter.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ NoteDBAdapter.COLUMN_NOTE_TITLE + " TEXT, "
					+ NoteDBAdapter.COLUMN_NOTE_TEXT + " TEXT)";
	private static final String CREATE_TABLE_BOATS =
			"CREATE TABLE " + BookDBAdapter.NOTE_TABLE
				+ " (" + BookDBAdapter.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BookDBAdapter.COLUMN_NOTE_PATH + " TEXT, "
			+ BookDBAdapter.COLUMN_NOTE_INFO + " TEXT)";


	SQLiteOpenHelper DBHelper;

	public DBAdapter(Context ctx) {
		DBHelper = new DatabaseHelper(ctx);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_CARS);
			db.execSQL(CREATE_TABLE_BOATS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Adding any table mods to this guy here
		}
	}

	public void close()	{
		DBHelper.close();
	}
}