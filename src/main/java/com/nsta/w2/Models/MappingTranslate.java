package com.nsta.w2.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mappingtranslate")
public class MappingTranslate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long w1;
	private long w2;
	
	public MappingTranslate(long id1, long id2) {
		this.w1 = id1;
		this.w2 = id2;
	}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getW1() {
		return w1;
	}
	public void setW1(int w1) {
		this.w1 = w1;
	}
	public long getW2() {
		return w2;
	}
	public void setW2(int w2) {
		this.w2 = w2;
	}
}
