package com.example.coursearchmos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coursearchmos.model.BookMarkModel;

import java.util.ArrayList;
import java.util.List;

public class BookMarksDBAdapter {
	public static final String BOOKMARKS_TABLE = "BOOKMARKS_TABLE";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_ID_BOOK = "BOOKMARKS_ID_BOOK";
	public static final String COLUMN_TITLE = "BOOKMARKS_TITLE";
	public static final String COLUMN_NUMBER_PAGE = "BOOKMARKS_NUMBER_PAGE";

	private final DBHelper dbHelper;

	public BookMarksDBAdapter(Context context) {
		dbHelper = new DBHelper(context.getApplicationContext());
	}

	public boolean addOne(BookMarkModel bookMarkModel) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_ID_BOOK, bookMarkModel.getBookId());
		cv.put(COLUMN_TITLE, bookMarkModel.getTitle());
		cv.put(COLUMN_NUMBER_PAGE, bookMarkModel.getBookPage());

		long insert = db.insert(BOOKMARKS_TABLE, null, cv);
		db.close();
		return insert != -1;
	}

	public boolean deleteOne(BookMarkModel bookMarkModel) {
		String queryString = "DELETE FROM " + BOOKMARKS_TABLE + " WHERE " + COLUMN_ID + " = " + bookMarkModel.getId();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		return cursor.moveToFirst();
	}

	public List<BookMarkModel> getAllByBookId(int id) {
		List<BookMarkModel> returnList = new ArrayList<>();

		String queryString = "SELECT * FROM " + BOOKMARKS_TABLE + " WHERE " + COLUMN_ID_BOOK + " = " + id;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			do {
				int bmID = cursor.getInt(0);
				int bmBookID = cursor.getInt(1);
				String bmTitle = cursor.getString(2);
				int bmNmPage = cursor.getInt(3);

				returnList.add(new BookMarkModel(bmID, bmBookID, bmTitle, bmNmPage));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return returnList;
	}

	public void close() {
		dbHelper.close();
	}
}

