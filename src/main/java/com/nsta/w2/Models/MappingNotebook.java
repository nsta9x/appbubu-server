package com.nsta.w2.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mappingnotebook")
public class MappingNotebook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long word_id;
	private long notebook_id;
	private String example_ids;
	
	public MappingNotebook(long id1, long id2, String exIds) {
		this.word_id 		= id1;
		this.notebook_id 	= id2;
		this.example_ids    = exIds;
	}
	
	public MappingNotebook() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWord_id() {
		return word_id;
	}

	public void setWord_id(long word_id) {
		this.word_id = word_id;
	}

	public long getNotebook_id() {
		return notebook_id;
	}

	public void setNotebook_id(long notebook_id) {
		this.notebook_id = notebook_id;
	}

	public String getExample_ids() {
		return example_ids;
	}
	
	public void setExample_ids(String example_ids) {
		this.example_ids = example_ids;
	}
}
