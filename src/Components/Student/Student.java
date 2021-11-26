/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import Utils.Props;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Student {
	protected String studentId;
	protected String name;
	protected String department;
	protected ArrayList<String> completedCoursesList = new ArrayList<String>();;

	public Student(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.studentId = stringTokenizer.nextToken();
		this.name = stringTokenizer.nextToken();
		this.name = this.name + Props.DIV + stringTokenizer.nextToken();
		this.department = stringTokenizer.nextToken();
		while (stringTokenizer.hasMoreTokens()) {
			this.completedCoursesList.add(stringTokenizer.nextToken());
		}
	}

	public boolean match(String studentId) {
		return this.studentId.equals(studentId);
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<String> getCompletedCourses() {
		return this.completedCoursesList;
	}
	public String getString() {
		String stringReturn = this.studentId + Props.DIV + this.name + Props.DIV + this.department;
		for (String s : this.completedCoursesList) stringReturn += Props.DIV + s.toString();
		return stringReturn;
	}
}
