/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University
 */

package Components.Student;

import Components.RmiConnection;
import Components.entity.NewStudent;
import Components.entity.RegisterCourse;
import Framework.*;
import Utils.Props;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentMain {


	public static void main(String[] args) throws IOException, NotBoundException {
		RMIEventBus eventBus = RmiConnection.getInstance();
		long componentId = eventBus.register();
		System.out.println(Props.STUDENT_MAIN_SUCCESS + componentId);

		StudentComponent studentsList = new StudentComponent(Props.STUDENTS_TXT);
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(Props.CLOCK_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
					case RegisterStudent:
						sendEvent(EventId.ClientOutput, registerStudent(studentsList, event.getMessage()), eventBus);
						break;
					case ListStudents :
						sendEvent(EventId.ClientOutput, makeStudentList(studentsList), eventBus);
						break;
					case DeleteStudent :
						sendEvent(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage()), eventBus);
						break;
					case EnrollCourseByStudent ://학생 아이디와 강좌 정보 객체가 들어온다.
						sendEvent(EventId.ClientOutput, courseEnrolment(studentsList, event.getMessage()), eventBus);
						break;
					case QuitTheSystem :
						sendEventQuit(EventId.QuitTheSystem, Props.QUIT_SYS, componentId, eventBus);
						done = true;
						break;
					default :
						break;
				}
			}
		}
	}

	private static String courseEnrolment(StudentComponent studentsList, String message) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		RegisterCourse registerCourse = om.readValue(message, RegisterCourse.class);
		if(!studentsList.isRegisteredStudent(registerCourse.getStudentId())) return Props.STD_NOT_REGI;
		Student student = studentsList.getStudentList().get(registerCourse.getStudentId());
		HashMap<String, ArrayList<String>> preCourseList = registerCourse.getPreCourseList();
		String registerCourseId = Props.EMPTY;
		for (String key : preCourseList.keySet()) {
			registerCourseId = key;
			ArrayList<String> preCs = preCourseList.get(key);
			for (String preC : preCs) if(!student.getCompletedCoursesList().contains(preC)) return Props.PRE_NOT_ENOUGH;
		}
		if(student.getCompletedCoursesList().contains(registerCourseId)) return Props.STD_ALREADY_COMP;
		writeRegisterCourse(registerCourse.getStudentId(), registerCourseId);
		student.addCompletedCourse(registerCourseId);
		return Props.STD_UPDATED;
	}

	private static String registerStudent(StudentComponent studentsList, String message) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		NewStudent newStudent = om.readValue(message, NewStudent.class);
		if(studentsList.isRegisteredStudent(newStudent.getStudentId())) return Props.STD_ALREADY_REGI;
		ArrayList<String> completedCoursesList = newStudent.getCompletedCoursesList();
		HashMap<String, ArrayList<String>> preCourseList = newStudent.getPreCourseList();
		for (String key : preCourseList.keySet()) {
			ArrayList<String> preCs = preCourseList.get(key);
			for (String preC : preCs) if(!completedCoursesList.contains(preC)) return Props.PRE_NOT_ENOUGH;
		}
		Student student = newStudent.makeStudent();
		studentsList.getStudentList().put(student.getStudentId(), student);
		return Props.STD_ADD;
	}

	public static void sendEvent(EventId eventId, String text, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text));
	}

	public static void sendEventQuit(EventId eventId, String text, long componentId, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text));
		eventBus.unRegister(componentId);
	}

	private static String deleteStudent(StudentComponent studentsList, String message) {
		String studentId = message.trim();
		if (studentsList.isRegisteredStudent(studentId)) {
			studentsList.vStudent.remove(studentId);
			return Props.STD_DELETED;
		}
		else return Props.STD_NOT_REGI;
	}

	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = Props.ENTER;
		HashMap<String, Student> studentList = studentsList.getStudentList();
		for (String key : studentList.keySet())
			returnString += studentList.get(key).getString()+Props.ENTER;
		return returnString;
	}

	private static void printLogEvent(Event event) {
		System.out.println(Props.EVENT_INFO_ID + event.getEventId() + Props.EVENT_INFO_MSG + event.getMessage());
	}

	public static void writeRegisterCourse(String studentId, String courseId){
		try {
			Files.write(Paths.get(Props.COURSE_ENROLLMENT_TXT),
					(studentId+Props.DIV+courseId).getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
