/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University
 */

package Components.Student;

import Components.RmiConnection;
import Framework.*;
import Utils.EntityUtil;
import Utils.Props;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

public class StudentMain {

	private final static EntityUtil entityUtil = new EntityUtil();

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
					case RegisterStudent:{
						String message = event.getMessage();
						if(message.split(Props.DIV)[1].equals(Props.OK)) registerWithOk(studentsList, message, eventBus);
						else register(studentsList, eventBus, message);
						break;
					}
					case ListStudents :
						sendEvent(EventId.ClientOutput, makeStudentList(studentsList), eventBus);
						break;
					case DeleteStudent :
						sendEvent(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage()), eventBus);
						break;
					case EnrollCourseByStudent :
						String message = event.getMessage();
						if(message.split(Props.DIV)[1].equals(Props.OK)) updateWithOk(studentsList, message, eventBus);
						else sendEvent(EventId.ClientOutput,courseEnrolment(studentsList, event.getMessage(), eventBus), eventBus);
						break;
					default :
						break;
				}
			}
		}
	}

	private static void updateWithOk(StudentComponent studentsList, String message, RMIEventBus eventBus) throws RemoteException {
		String[] split = message.split(Props.DIV);
		Student student = studentsList.getStudentList().get(split[1]);
		String updated = student.getString()+Props.DIV+split[2];
		Student updatedStudent = new Student(updated);
		studentsList.vStudent.replace(student.getStudentId(), updatedStudent);
		sendEvent(EventId.ClientOutput, Props.STD_ADD, eventBus);
	}

	private static void register(StudentComponent studentsList, RMIEventBus eventBus, String message) throws RemoteException {
		Student student = new Student(message);
		if(student.hasCourseInfo()) sendEvent(EventId.ValidateCourses, message, eventBus);
		else{
			Student student1 = new Student(message);
			studentsList.vStudent.put(student1.getStudentId(), student1);
			sendEvent(EventId.ClientOutput, Props.STD_ADD, eventBus);
		}
	}

	private static void registerWithOk(StudentComponent studentsList, String message, RMIEventBus eventBus) throws RemoteException {
		Student student = Student.makeStudentWithOk(message);
		studentsList.vStudent.put(student.getStudentId(), student);
		sendEvent(EventId.ClientOutput, Props.STD_ADD, eventBus);
	}

	private static String courseEnrolment(StudentComponent studentsList, String message, RMIEventBus eventBus) throws RemoteException {
		String studentId = message.split(Props.DIV)[0];
		String courseId = message.split(Props.DIV)[1];
		if(!studentsList.isRegisteredStudent(studentId)) return Props.STD_NOT_REGI;
		String studentStr = studentsList.getStudentList().get(studentId).getString();
		if(studentStr.contains(courseId)) return Props.STD_ALREADY_COMP;
		if(entityUtil.validatePreCourse(studentStr, message)) {
			sendEvent(EventId.ValidateCourse, message, eventBus);
			return Props.STD_CHECK_COURSE;
		}
		else return Props.PRE_NOT_ENOUGH;
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
}
