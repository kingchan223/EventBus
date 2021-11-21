/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import Components.Props;
import Components.Student.Student;
import Framework.*;

public class CourseMain {

	public static void main(String[] args) throws IOException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(Props.PORT);
		RMIEventBus eventBus = (RMIEventBus)registry.lookup(Props.LOOKUP);
		long componentId = eventBus.register();
		System.out.println(Props.COURSE_MAIN_SUCCESS + componentId);

		CourseComponent coursesList = new CourseComponent(Props.COURSES_TXT);
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
					case Course -> courseMethods(event, coursesList, eventBus);
					case QuitTheSystem -> {
						sendEventQuit(EventId.QuitTheSystem, Props.QUIT_SYS, componentId, eventBus);
						done = true;
					}
					default -> {
					}
				}
			}
		}
	}

	public static void courseMethods(Event event, CourseComponent coursesList, RMIEventBus eventBus) throws RemoteException {
		printLogEvent(event);
		switch (event.getMethod()) {
			case CREATE -> sendEvent(EventId.ClientOutput, registerCourse(coursesList, event.getMessage()), Method.CREATE, eventBus);
			case READ -> sendEvent(EventId.ClientOutput, makeCourseList(coursesList), Method.READ, eventBus);
			case DELETE -> sendEvent(EventId.ClientOutput, deleteCourse(coursesList, event.getMessage()), Method.DELETE, eventBus);
			case UPDATE -> {
				if(coursesList.isRegisteredCourse(event.getMessage().split(Props.DIV)[1]))
					sendEvent(EventId.Student, courseInfoLine(coursesList, event.getMessage()), Method.UPDATE, eventBus);
				else
					sendEvent(EventId.ClientOutput,  Props.COURSE_NOT_REGI, null, eventBus);
			}
			default -> {}
		}
	}

	private static String courseInfoLine(CourseComponent coursesList, String message) {
		String courseId = message.split(Props.DIV)[1];
		String studentId = message.split(Props.DIV)[0];
		return studentId+Props.DIV+coursesList.getCourseList().get(courseId).getString();
	}

	public static void sendEvent(EventId eventId, String text, Method method, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text, method));
	}

	public static void sendEventQuit(EventId eventId, String text, long componentId, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text, null));
		eventBus.unRegister(componentId);
	}

	private static String deleteCourse(CourseComponent coursesList, String message) {
		String courseId = message.trim();
		if (coursesList.isRegisteredCourse(courseId)) {
			for (int i = 0; i < coursesList.vCourse.size(); i++)
				if(coursesList.vCourse.get(i).courseId.equals(courseId)){
					coursesList.vCourse.remove(i);
					break;
				}
			return Props.COURSE_DELETED;
		} else
			return Props.COURSE_NOT_REGI;
	}

	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.put(course.courseId, course);
			return Props.COURSE_ADD;
		} else
			return Props.COURSE_ALREADY_REGI;
	}

	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = Props.EMPTY;
		HashMap<String, Course> courseList = coursesList.getCourseList();
		for (String key : courseList.keySet())
			returnString += courseList.get(key).getString()+Props.ENTER;
		return returnString;
	}

	private static void printLogEvent(Event event) {
		System.out.println(event.getMethod() + Props.EVENT_INFO_ID + event.getEventId() + Props.EVENT_INFO_MSG + event.getMessage());
	}
}
