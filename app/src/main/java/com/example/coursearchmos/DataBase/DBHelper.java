package com.example.coursearchmos.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "reader.db";
	public static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_NOTES =
			"CREATE TABLE " + NoteDBAdapter.NOTE_TABLE
					+ " (" + NoteDBAdapter.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ NoteDBAdapter.COLUMN_NOTE_TITLE + " TEXT, "
					+ NoteDBAdapter.COLUMN_NOTE_TEXT + " TEXT, "
					+ NoteDBAdapter.COLUMN_NOTE_ID_BOOK + " INTEGER)";
	private static final String CREATE_TABLE_BOOKS =
			"CREATE TABLE " + BookDBAdapter.BOOK_TABLE
					+ " (" + BookDBAdapter.COLUMN_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ BookDBAdapter.COLUMN_BOOK_PATH + " TEXT, "
					+ BookDBAdapter.COLUMN_BOOK_INFO + " TEXT, "
					+ BookDBAdapter.COLUMN_BOOK_LAST_CUR_PAGE + " INTEGER, "
					+ BookDBAdapter.COLUMN_BOOK_PAGE_COUNT + " INTEGER, "
					+ BookDBAdapter.COLUMN_BOOK_TIME + " INTEGER)";
	private static final String CREATE_TABLE_BOOKMARK =
			"CREATE TABLE " + BookMarksDBAdapter.BOOKMARKS_TABLE
					+ " (" + BookMarksDBAdapter.COLUMN_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ BookMarksDBAdapter.COLUMN_ID_BOOK + " INTEGER, "
					+ BookMarksDBAdapter.COLUMN_TITLE + " TEXT, "
					+ BookMarksDBAdapter.COLUMN_NUMBER_PAGE + " INTEGER)";


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NOTES);
		db.execSQL(CREATE_TABLE_BOOKS);
		db.execSQL(CREATE_TABLE_BOOKMARK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Adding any table mods to this guy here
	}
}