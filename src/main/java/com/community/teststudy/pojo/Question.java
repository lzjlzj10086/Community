package com.community.teststudy.pojo;


public class Question {
	int id;
	String subject;
	String item1;
	String item2;
	String item3;
	String item4;
	String answer;
	String usercode;
	private Long createTime;

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public Question() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Question{" +
				"id=" + id +
				", subject='" + subject + '\'' +
				", item1='" + item1 + '\'' +
				", item2='" + item2 + '\'' +
				", item3='" + item3 + '\'' +
				", item4='" + item4 + '\'' +
				", answer='" + answer + '\'' +
				", usercode='" + usercode + '\'' +
				'}';
	}
}
