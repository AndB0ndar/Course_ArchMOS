package com.example.coursearchmos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.coursearchmos.model.UserModel;

import java.security.MessageDigest;
import java.util.Objects;

public class UserDBAdapter {
	public static final String USER_TABLE = "USER_TABLE";
	public static final String COLUMN_USER_NAME = "USER_NAME";
	public static final String COLUMN_USER_HASH = "USER_HASH";
	public static final String COLUMN_USER_F_IS_CHILD = "USER_F_IS_CHILD";
	public static final String COLUMN_ID = "ID";

	private DBHelper dbHelper;


	public UserDBAdapter(Context context) {
		dbHelper = new DBHelper(context.getApplicationContext());
	}

	public boolean addOne(String login, String password, boolean isChild) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_USER_NAME, login);
		cv.put(COLUMN_USER_HASH, calculateMD5Hash(password));
		cv.put(COLUMN_USER_F_IS_CHILD, isChild);

		long insert = db.insert(USER_TABLE, null, cv);
		db.close();
		return insert != -1;
	}

	public UserModel getById(int id) {
		UserModel userModel = null;

		String queryString = "SELECT * FROM " + USER_TABLE
				+ " WHERE " + COLUMN_ID + " = " + id;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			int userId = cursor.getInt(0);
			String userLogin = cursor.getString(1);
			String userHash = cursor.getString(2);
			boolean userIsChild = cursor.getInt(3) > 1;

			userModel = new UserModel(userId, userLogin, userHash, userIsChild);
		}

		cursor.close();
		db.close();

		return userModel;
	}

	public UserModel logIn(String login, String password) {
		if (isEmpty())
			return null;
		UserModel user = null;

		String queryString = "SELECT * FROM " + USER_TABLE
				+ " WHERE " + COLUMN_USER_NAME + " = " + login;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()) {
			int userId = cursor.getInt(0);
			String userLogin = cursor.getString(1);
			String userHash = cursor.getString(2);
			boolean userIsChild = cursor.getInt(3) == 1;

			user = new UserModel(userId, userLogin, userHash, userIsChild);
		}

		cursor.close();
		db.close();

		if (user != null) {
			String enteredHash = calculateMD5Hash(password);
			return Objects.equals(enteredHash, user.getHash()) ? user : null;
		}
		return null;
	}

	public boolean isEmpty() {
		String queryString = "SELECT EXISTS (SELECT 1 FROM "
				+ USER_TABLE + ")";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		int ret = 0;
		if (cursor.moveToFirst()){
			ret = cursor.getInt(0);
		}

		cursor.close();
		db.close();

		return ret == 0;
	}

	public boolean isExist(String login) {
		if (isEmpty())
			return false;

		String queryString = "SELECT * FROM " + USER_TABLE
				+ " WHERE " + COLUMN_USER_NAME + " = " + login;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);

		boolean resule = cursor.moveToFirst();
		cursor.close();
		db.close();

		return resule;
	}

	@Nullable
	private static String calculateMD5Hash(String data) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("md5");
			digest.update(data.getBytes());
			final byte[] bytes = digest.digest();
			final StringBuilder sb = new StringBuilder();
			for (byte dataByte : bytes) {
				sb.append(String.format("%02X", dataByte));
			}
			return sb.toString().toLowerCase();
		} catch (Exception exc) {
			return null;
		}
	}

	public void close() {
		dbHelper.close();
	}
}
