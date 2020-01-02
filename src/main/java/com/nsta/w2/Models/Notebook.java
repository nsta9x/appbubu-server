package com.nsta.w2.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notebook {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long  id;
	private String name;
	private String description;
	private long user_id;
	private long lang_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public long getLang_id() {
		return lang_id;
	}
	public void setLang_id(int lang_id) {
		this.lang_id = lang_id;
	}
	public Notebook() {};
	public Notebook(String name, long user_id, long lang_id, String description) {
		this.name = name;
		this.user_id = user_id;
		this.lang_id = lang_id;
		this.description = description;
	}
	
}
