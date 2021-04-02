package com.community.teststudy.pojo;


public class Answer {

	int key;
	String value;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Answer [key=" + key + ", value=" + value + "]";
	}
	

}
