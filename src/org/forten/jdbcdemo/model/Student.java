package org.forten.jdbcdemo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.forten.util.DateUtil;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private int score;
	private Date registTime;

	public Student() {
		super();
		this.registTime = new Date();
	}

	public Student(String name, String gender, Date birthday, String email, int score) {
		this();
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public java.sql.Date getBirthdayForDB() {
		return DateUtil.convertToSqlDate(birthday);
	}

	public Timestamp getRegistTimeForDB() {
		return DateUtil.convertToTimestamp(registTime);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", email="
				+ email + ", score=" + score + ", registTime=" + registTime + "]";
	}
}
