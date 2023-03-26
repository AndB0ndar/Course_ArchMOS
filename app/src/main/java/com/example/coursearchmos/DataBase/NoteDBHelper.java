package com.example.coursearchmos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursearchmos.model.NoteModel;

import java.util.ArrayList;
import java.util.List;


public class NoteDBHelper extends SQLiteOpenHelper {

	public static final String NOTE_TABLE = "NOTE_TABLE";
	public static final String COLUMN_NOTE_TITLE = "NOTE_TITLE";
	public static final String COLUMN_NOTE_TEXT = "NOTE_TEXT";
	public static final String COLUMN_ID = "ID";

	public NoteDBHelper(Context context) {
		super(context, "note.db", null, 1);
	}

	// this is called the first time a database is accessed
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableStatement = "CREATE TABLE " + NOTE_TABLE
				+ " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_NOTE_TITLE + " TEXT, "
				+ COLUMN_NOTE_TEXT + " TEXT)";

		db.execSQL(createTableStatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean addOne(NoteModel noteModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_NOTE_TITLE, noteModel.getTitle());
		cv.put(COLUMN_NOTE_TEXT, noteModel.getText());

		long insert = db.insert(NOTE_TABLE, null, cv);
		db.close();
		return insert != -1;
	}

	public boolean deleteOne(NoteModel noteModel) {
		String queryString = "DELETE FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + noteModel.getId();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		return cursor.moveToFirst();
	}

	public boolean updateOne(NoteModel noteModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_ID, noteModel.getId());
		cv.put(COLUMN_NOTE_TITLE, noteModel.getTitle());
		cv.put(COLUMN_NOTE_TEXT, noteModel.getText());

		db.update(NOTE_TABLE, cv, "id = ?", new String[]{String.valueOf(noteModel.getId())});
		db.close();
		return true;
	}

	public List<NoteModel> getAll() {
		List<NoteModel> returnList = new ArrayList<>();

		String queryString = "SELECT * FROM " + NOTE_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			// loop through the cursor and create new Note object
			do {
				int noteID = cursor.getInt(0);
				String noteTITLE = cursor.getString(1);
				String noteTEXT = cursor.getString(2);

				NoteModel noteModel = new NoteModel(noteID, noteTITLE, noteTEXT);
				returnList.add(noteModel);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return returnList;
	}

	public NoteModel getById(int id) {
		NoteModel noteModel = null;

		String queryString = "SELECT * FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			int noteID = cursor.getInt(0);
			String noteTITLE = cursor.getString(1);
			String noteTEXT = cursor.getString(2);

			noteModel = new NoteModel(noteID, noteTITLE, noteTEXT);
		}

		cursor.close();
		db.close();

		return noteModel;
	}
}
