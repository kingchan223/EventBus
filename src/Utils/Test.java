package Utils;

import Components.InputType;
import Components.Props;
import Framework.Event;
import Framework.EventId;
import domain.Course;
import domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Test {
    private static String makeStudentInfo() throws IOException {
        Util util = new Util();
        System.out.println(Props.STD_ID_MSG);
        String id = util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.STD_ID_LEN);
        System.out.println(Props.STD_FMNAME_MSG);
        String familyName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.FamilyName);
        System.out.println(Props.STD_FNAME_MSG);
        String firstName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.FirstName);
        System.out.println(Props.STD_DEPART_MSG);
        String department = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.Department);
        System.out.println(Props.STD_COMP_COURSE_MSG);
        String compCourseIds = inputCourseIds();
        String studentInfo = Student.makeStudent(id, firstName, familyName, department, compCourseIds);
        Props.printUserInput(studentInfo);
        return studentInfo;
    }

    private static String inputCourseIds() throws IOException {
        Util util = new Util();
        return util.validateCourseIds(new BufferedReader(new InputStreamReader(System.in)).readLine());
    }

    private static String makeCourseInfo() throws IOException {
        Util util = new Util();
        System.out.println(Props.COURSE_ID_MSG);
        String id = util.validateId(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), Props.COURSE_ID_LEN);
        System.out.println(Props.COURSE_P_FMNAME_MSG);
        String familyName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.FamilyName);
        System.out.println(Props.COURSE_NAME_MSG);
        String courseName = util.validateStr(new BufferedReader(new InputStreamReader(System.in)).readLine().trim(), InputType.CourseName);
        System.out.println(Props.COURSE_PRECOURSE_MSG);
        String preCourseIds= inputCourseIds();
        String courseInfo = Course.makeCourse(id, familyName, courseName, preCourseIds);
        Props.printUserInput(courseInfo);
        return courseInfo;
    }

    private static void writeMenu() {
        System.out.println("1. List Students");
        System.out.println("2. List Courses");
        System.out.println("3. Register a new Student");
        System.out.println("4. Register a new Course");
        System.out.println("5. Delete a Student");
        System.out.println("6. Delete a Course");
        System.out.println("0. Quit the system");
        System.out.print("\n Choose No.: ");
    }
    private static void printWrongMenu(){
        System.out.println("Please select menu again");
    }
    public static void main(String[] args) throws IOException {
        boolean done = false;
        while (!done) {
            writeMenu();
            try {
                switch (new BufferedReader(new InputStreamReader(System.in)).readLine().trim()) {
                    case "1"://student list
                        break;
                    case "2"://course list
                        break;
                    case "3"://register student
                        String s = makeStudentInfo();
                        System.out.println(s+"---");
                        break;
                    case "4"://register course
                        String s1 = makeCourseInfo();
                        System.out.println(s1+"---");
                        break;
                    case "0"://quit system
                        done = true;
                        break;
                    default:
                        // TODO
                        //잘못된 메뉴 입력 메세지
                        printWrongMenu();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
