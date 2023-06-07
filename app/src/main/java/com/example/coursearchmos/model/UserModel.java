package com.example.coursearchmos.model;

import java.io.Serializable;

public class UserModel implements Serializable {
	private final int id;
	private final String name;
	private final String hash;
	private final Boolean isChild;


	public UserModel(int id, String name, String hash, Boolean isChild) {
		this.id = id;
		this.name = name;
		this.hash = hash;
		this.isChild = isChild;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHash() {
		return hash;
	}

	public Boolean getChild() {
		return isChild;
	}
}
