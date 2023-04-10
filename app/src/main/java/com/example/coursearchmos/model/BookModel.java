package com.example.coursearchmos.model;

import java.io.Serializable;

public class BookModel implements Serializable {
	private int id;
	private String path, info;

	public BookModel(int id, String path, String info) {
		this.id = id;
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
