package com.nsta.w2.Models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Word{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column
    private String content;
	
	@Column
    private int type;
	
	@Column
    private String note;
	
	@Column
	private int lang_id;

	@Transient
	private List<Word> translate = new ArrayList<>();
	
	@Transient
	private List<Word> related   = new ArrayList<>();
	
	//@Transient
	//private long notebook_id;
	
	@Transient
	private List<String> examples = new ArrayList<>();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public int getLang_id() {
		return lang_id;
	}

	public void setLang_id(int lang_id) {
		this.lang_id = lang_id;
	}
	
	@Transient
	public List<Word> getRelated() {
		return related;
	}

	public void setRelated(List<Word> related) {
		this.related = related;
	}
	
	@Transient
	public List<Word> getTranslate() {
		return translate;
	}

	public void setTranslate(List<Word> translate) {
		this.translate = translate;
	}
	
//	@Transient
//	public long getNotebook_id() {
//		return notebook_id;
//	}
//
//	public void setNotebook_id(long notebook_id) {
//		this.notebook_id = notebook_id;
//	}
	
	@Transient
	public List<String> getExamples() {
		return examples;
	}

	public void setExamples(List<String> ex) {
		this.examples = ex;
	}
}
