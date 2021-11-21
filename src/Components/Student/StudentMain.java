/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import Components.Props;
import Framework.*;
import Utils.EntityUtil;

public class StudentMain {

	private final static EntityUtil entityUtil = new EntityUtil();

	public static void main(String[] args) throws IOException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(Props.PORT);
		RMIEventBus eventBus = (RMIEventBus)registry.lookup(Props.LOOKUP);
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
					case Student -> studentMethods(event, studentsList, eventBus);
					case QuitTheSystem -> {
						sendEventQuit(EventId.QuitTheSystem, Props.QUIT_SYS, componentId, eventBus);
						done = true;
					}
					default -> {}
				}
			}
		}
	}

	public static void studentMethods(Event event, StudentComponent studentsList, RMIEventBus eventBus) throws RemoteException {
		printLogEvent(event);
		switch (event.getMethod()) {
			case CREATE -> sendEvent(EventId.ClientOutput, registerStudent(studentsList, event.getMessage()), Method.CREATE, eventBus);
			case READ -> sendEvent(EventId.ClientOutput, makeStudentList(studentsList), Method.READ, eventBus);
			case DELETE -> sendEvent(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage()), Method.DELETE, eventBus);
			case UPDATE -> sendEvent(EventId.ClientOutput, courseEnrolment(studentsList, event.getMessage()), Method.UPDATE, eventBus);
			default -> {}
		}
	}

	private static String courseEnrolment(StudentComponent studentsList, String message) {
		String studentId = message.split(Props.DIV)[0];
		String courseId = message.split(Props.DIV)[1];
		if(!studentsList.isRegisteredStudent(studentId)) return Props.STD_NOT_REGI;

		String studentStr = studentsList.getStudentList().get(studentId).getString();
		if(entityUtil.validatePreCourse(studentStr, message)){
			String student = studentsList.getStudentList().get(studentId).getString()+Props.DIV+courseId;
			studentsList.getStudentList().replace(studentId, new Student(student));
			return Props.STD_UPDATED;
		}
		else
			return Props.PRE_NOT_ENOUGH;
	}

	public static void sendEvent(EventId eventId, String text, Method method, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text, method));
	}

	public static void sendEventQuit(EventId eventId, String text, long componentId, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text, null));
		eventBus.unRegister(componentId);
	}

	private static String deleteStudent(StudentComponent studentsList, String message) {
		String studentId = message.trim();
		if (studentsList.isRegisteredStudent(studentId)) {
			studentsList.vStudent.remove(studentId);
			return Props.STD_DELETED;
		} else
			return Props.STD_NOT_REGI;
	}

	private static String registerStudent(StudentComponent studentsList, String message) {
		Student student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.put(student.studentId, student);
			return Props.STD_ADD;
		}else
			return Props.STD_ALREADY_REGI;
	}

	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = Props.EMPTY;
		HashMap<String, Student> studentList = studentsList.getStudentList();
		for (String key : studentList.keySet())
			returnString += studentList.get(key).getString()+Props.ENTER;
		return returnString;
	}

	private static void printLogEvent(Event event) {
		System.out.println(event.getMethod() + Props.EVENT_INFO_ID + event.getEventId() + Props.EVENT_INFO_MSG + event.getMessage());
	}
}
