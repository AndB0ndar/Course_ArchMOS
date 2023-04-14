package com.example.coursearchmos.model;

import java.io.Serializable;

public class BookModel implements Serializable {
	private int id;
	private String title;
	private String path;
	private String info;

	public BookModel(int id, String path, String info) {
		this.id = id;
		String[] s = path.split("/");
		this.title = s[s.length - 1].split("\\.")[0];
		this.path = path;
		this.info = info;
	}

	public BookModel() {
	}

	@Override
	public String toString() {
		return "BookModel{" +
				"id=" + id +
				", path='" + path + '\'' +
				", info='" + info + '\'' +
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
