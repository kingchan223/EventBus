/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import Components.RmiConnection;
import Components.entity.NewStudent;
import Components.entity.RegisterCourse;
import Framework.*;
import Utils.Props;
import Utils.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseMain {
	public static void main(String[] args) throws IOException, NotBoundException {
		RMIEventBus eventBus = RmiConnection.getInstance();
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
					case RegisterCourse :
						sendEvent(EventId.ClientOutput, registerCourse(coursesList, event.getMessage()), eventBus);
						break;
					case ListCourses :
						sendEvent(EventId.ClientOutput, makeCourseList(coursesList),eventBus);
						break;
					case DeleteCourse :
						sendEvent(EventId.ClientOutput, deleteCourse(coursesList, event.getMessage()),  eventBus);
						break;
					case AddPreCoursesInfo :
						sendEvent(EventId.RegisterStudent,makeNewStudentJson(coursesList, event.getMessage()),  eventBus);
						break;
					case AddPreCourseInfo :
						sendEvent(EventId.EnrollCourseByStudent,makeRegisterCourseJson(coursesList, event.getMessage()),  eventBus);
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

	private static String makeRegisterCourseJson(CourseComponent coursesList, String message) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		String courseId = message.split(Props.DIV)[1];
		RegisterCourse registerCourse = new RegisterCourse();
		registerCourse.setStudentId(message.split(Props.DIV)[0]);
		registerCourse.addPreCourseOf(courseId, coursesList.getPreCourseOf(courseId));
		return om.writeValueAsString(registerCourse);
	}

	private static String makeNewStudentJson(CourseComponent coursesList, String message) throws JsonProcessingException {
		Util util = new Util();
		ObjectMapper om = new ObjectMapper();
		ArrayList<String> courseIds = util.extCourseInfoFrom(message);
		NewStudent newStudent = new NewStudent(message);
		for (String courseId : courseIds) newStudent.addPreCourseOf(courseId, coursesList.getPreCourseOf(courseId));
		return om.writeValueAsString(newStudent);
	}

	private static boolean validateCourses(String message) {
		Util util = new Util();
		return util.validatePreCourse(message);
	}

	private static String courseInfoLine(CourseComponent coursesList, String message) {
		String courseId = message.split(Props.DIV)[1];
		String studentId = message.split(Props.DIV)[0];
		return studentId+Props.DIV+coursesList.getCourseList().get(courseId).getString();
	}

	public static void sendEvent(EventId eventId, String text,RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text));
	}

	public static void sendEventQuit(EventId eventId, String text, long componentId, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text));
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
		} else return Props.COURSE_ALREADY_REGI;
	}

	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = Props.ENTER;
		HashMap<String, Course> courseList = coursesList.getCourseList();
		for (String key : courseList.keySet())
			returnString += courseList.get(key).getString()+Props.ENTER;
		return returnString;
	}

	private static void printLogEvent(Event event) {
		System.out.println(Props.EVENT_INFO_ID + event.getEventId() + Props.EVENT_INFO_MSG + event.getMessage());
	}
}
