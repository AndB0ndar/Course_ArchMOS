package com.example.coursearchmos.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Note implements Parcelable {
	String title, text;

	public Note(String title, String text) {
		this.title = title;
		this.text = text;
	}

	protected Note(Parcel in) {
		title = in.readString();
		text = in.readString();
	}

	public static final Creator<Note> CREATOR = new Creator<Note>() {
		@Override
		public Note createFromParcel(Parcel in) {
			return new Note(in);
		}

		@Override
		public Note[] newArray(int size) {
			return new Note[size];
		}
	};

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(text);
	}
}
