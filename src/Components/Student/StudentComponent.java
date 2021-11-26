/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import Utils.Props;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class StudentComponent {
	protected HashMap<String, Student> vStudent;
	
	public StudentComponent(String sStudentFileName) throws FileNotFoundException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(sStudentFileName));
		this.vStudent = new LinkedHashMap<>();
		while (bufferedReader.ready()) {
			String stuInfo = bufferedReader.readLine();
			if (!stuInfo.equals(Props.EMPTY)) this.vStudent.put(stuInfo.split(Props.DIV)[0], new Student(stuInfo));
		}
		bufferedReader.close();
	}
	public HashMap<String, Student> getStudentList() {
		return vStudent;
	}
	public void setStudent(HashMap<String, Student> vStudent) {
		this.vStudent = vStudent;
	}
	public boolean isRegisteredStudent(String sSID) {
		return vStudent.get(sSID) != null;
	}
}
