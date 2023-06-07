package com.example.coursearchmos.model;

import java.io.Serializable;

public class NoteModel implements Serializable {
	private int id;
	private String title, text;
	private final int idBook;

	public NoteModel(int id, String title, String text, int idBook) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.idBook = idBook;
	}

	@Override
	public String toString() {
		return "NoteModel{" +
				"id=" + id +
				", title='" + title + '\'' +
				", text='" + text + '\'' +
				", idBook=" + idBook +
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

	public int getIdBook() {
		return idBook;
	}
}
