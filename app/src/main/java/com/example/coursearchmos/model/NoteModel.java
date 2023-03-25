package com.example.coursearchmos.model;

import java.io.Serializable;

public class NoteModel implements Serializable {
	private int id;
	private String title, text;

	public NoteModel(int id, String title, String text) {
		this.id = id;
		this.title = title;
		this.text = text;
	}

	public NoteModel() {
	}

	@Override
	public String toString() {
		return "NoteModel{" +
				"id=" + id +
				", title='" + title + '\'' +
				", text='" + text + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
}
