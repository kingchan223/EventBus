/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.ClientInput;

import Utils.InputType;
import Components.RmiConnection;
import Framework.*;
import Utils.EntityUtil;
import Utils.Props;
import Utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;

/*Send Event*/
public class ClientInputMain {

	private static final Util util = new Util();
	private static final EntityUtil eUtil = new EntityUtil();

	public static void main(String[] args) throws RemoteException, NotBoundException{
		RMIEventBus eventBus = RmiConnection.getInstance();
//		System.out.println("eventBus = " + eventBus);
		long componentId = eventBus.register();
		System.out.println(Props.CLIENT_INPUT_SUCCESS+ componentId);
		boolean done = false;
		while (!done) {
			writeMenu();
			try {
				switch (new BufferedReader(new InputStreamReader(System.in)).readLine().trim()) {
					case Props.MENU_N1:
						sendEvent(EventId.ListStudents, null, eventBus);
						break;
					case Props.MENU_N2:
						sendEvent(EventId.ListCourses, null, eventBus);
						break;
					case Props.MENU_N3 :
						sendEvent(EventId.RegisterStudent, makeStudentInfo(), eventBus);
						break;
					case Props.MENU_N4 :
						sendEvent(EventId.RegisterCourse, makeCourseInfo(), eventBus);
						break;
					case Props.MENU_N5 :
						sendEvent(EventId.DeleteStudent, deleteStudentID(), eventBus);
						break;
					case Props.MENU_N6 :
						sendEvent(EventId.DeleteCourse, deleteCourseID(),eventBus);
						break;
					case Props.MENU_N7 :
						sendEvent(EventId.EnrollCourseByStudent, courseEnrolment(), eventBus);
						break;
					case Props.MENU_N0 :
						sendEventQuit(EventId.QuitTheSystem, Props.QUIT_SYS, componentId, eventBus);
						done = true;
						break;
					default :
						printWrongMenu();
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendEvent(EventId eventId, String text, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text));
		printLogSend(eventId);
	}

	public static void sendEventQuit(EventId eventId, String text, long componentId, RMIEventBus eventBus) throws RemoteException {
		eventBus.sendEvent(new Event(eventId, text));
		eventBus.unRegister(componentId);
		printLogSend(eventId);
	}

	private static String courseEnrolment() throws IOException {
		System.out.println(Props.STD_ID_MSG);
		String studentId = util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.STD_ID_LEN);
		System.out.println(Props.COURSE_ID_MSG);
		String courseId = util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.COURSE_ID_LEN);
		return studentId + Props.DIV + courseId;
	}

	private static String deleteCourseID() throws IOException {
		System.out.println(Props.COURSE_ID_MSG);
		return util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.COURSE_ID_LEN);
	}

	private static String deleteStudentID() throws IOException {
		System.out.println(Props.STD_ID_MSG);
		return util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.STD_ID_LEN);
	}

	private static String makeStudentInfo() throws IOException {
		System.out.println(Props.STD_ID_MSG);
		String id = util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.STD_ID_LEN);
		System.out.println(Props.STD_FMNAME_MSG);
		String familyName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.FamilyName);
		System.out.println(Props.STD_FNAME_MSG);
		String firstName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.FirstName);
		System.out.println(Props.STD_DEPART_MSG);
		String department = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.Department);
		System.out.println(Props.STD_COMP_COURSE_MSG);
		String compCourseIds = util.validateCourseIds(new BufferedReader(new InputStreamReader(System.in)).readLine());
		String studentInfo = eUtil.makeStudentStr(id, firstName, familyName, department, compCourseIds);
		Props.printUserInput(studentInfo);
		return studentInfo;
	}

	private static String makeCourseInfo() throws IOException {
		System.out.println(Props.COURSE_ID_MSG);
		String id = util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.COURSE_ID_LEN);
		System.out.println(Props.COURSE_P_FMNAME_MSG);
		String familyName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.FamilyName);
		System.out.println(Props.COURSE_NAME_MSG);
		String courseName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.CourseName);
		System.out.println(Props.COURSE_PRECOURSE_MSG);
		String preCourseIds= util.validateCourseIds(new BufferedReader(new InputStreamReader(System.in)).readLine());
		String courseInfo = eUtil.makeCourseStr(id, familyName, courseName, preCourseIds);
		Props.printUserInput(courseInfo);
		return courseInfo;
	}

	private static void writeMenu() {
		System.out.println(Props.MENU1);
		System.out.println(Props.MENU2);
		System.out.println(Props.MENU3);
		System.out.println(Props.MENU4);
		System.out.println(Props.MENU5);
		System.out.println(Props.MENU6);
		System.out.println(Props.MENU7);
		System.out.println(Props.MENU0);
		System.out.print(Props.MENU_C);
	}

	private static void printLogSend(EventId eventId) {
		System.out.println(Props.SEND_E + eventId);
	}

	private static void printWrongMenu(){
		System.out.println(Props.SELECT_AGAIN);
	}
}
