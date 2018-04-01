package org.forten.jdbcdemo.test;

import java.util.Date;

import org.forten.jdbcdemo.bo.StudentBo;
import org.forten.jdbcdemo.model.Student;

public class StudentBoTest {

	public static void main(String[] args) {
		Student s = new Student("张飞", "M", new Date(), "zhangfei@sohu.com", 326);
		StudentBo.doSave(s);
		// StudentBo.doDelete(6);

		// s.setName("嫦娥");
		// s.setGender("F");
		// s.setBirthday(DateUtil.convertToDate("1999/08/30", "yyyy/MM/dd"));
		// s.setEmail("moonbueaty@126.com");
		// s.setScore(552);
		// s.setId(5);

		// StudentBo.doUpdate(s);

		// List<Student> list = StudentBo.queryByName("");
		// list.forEach(System.out::println);
		// System.out.println("--------------------------------");
		// list = StudentBo.queryByName("张");
		// list.forEach(System.out::println);
	}

}
