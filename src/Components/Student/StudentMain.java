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

import Components.Props;
import Framework.*;

public class StudentMain {



	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
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
			default -> {}
		}
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
			for (int i = 0; i < studentsList.vStudent.size(); i++)
				if(studentsList.vStudent.get(i).studentId.equals(studentId)){
					studentsList.vStudent.remove(i);
					break;
				}
			return Props.STD_DELETED;
		} else
			return Props.STD_NOT_REGI;
	}

	private static String registerStudent(StudentComponent studentsList, String message) {
		Student student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			return Props.STD_ADD;
		} else
			return Props.STD_ALREADY_REGI;
	}

	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = Props.EMPTY;
		for (int j = 0; j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + Props.ENTER;
		}
		return returnString;
	}

	private static void printLogEvent(Event event) {
		System.out.println(event.getMethod() + Props.EVENT_INFO_ID + event.getEventId() + Props.EVENT_INFO_MSG + event.getMessage());
	}
}
