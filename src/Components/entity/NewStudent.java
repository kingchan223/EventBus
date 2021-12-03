package Components.entity;

import Components.Student.Student;
import Utils.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class NewStudent implements Serializable{

    public static final Long serialVersionUID = 1000L;

    String studentId;
    String name;
    String department;
    ArrayList<String> completedCoursesList = new ArrayList<String>();
    HashMap<String, ArrayList<String>> preCourseList = new LinkedHashMap<>();

    public NewStudent() {}

    public NewStudent(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
        this.studentId = stringTokenizer.nextToken();
        this.name = stringTokenizer.nextToken();
        this.name = this.name + Props.DIV + stringTokenizer.nextToken();
        this.department = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            this.completedCoursesList.add(stringTokenizer.nextToken());
        }
    }

    public Student makeStudent(){
        Student student = new Student();
        student.setStudentId(this.studentId);
        student.setName(this.name);
        student.setDepartment(this.department);
        student.setCompletedCoursesList(this.completedCoursesList);
        return student;
    }

    public void addPreCourseOf(String courseId, String preCourseId){
        if(!this.preCourseList.containsKey(courseId)) preCourseList.get(courseId).add(preCourseId);
    }

    public void addPreCourseOf(String courseId, ArrayList<String> preCourseIds){
        if(!this.preCourseList.containsKey(courseId)) this.preCourseList.put(courseId, preCourseIds);
    }

    public HashMap<String, ArrayList<String>> getPreCourseList() {
        return preCourseList;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public ArrayList<String> getCompletedCoursesList() {
        return completedCoursesList;
    }
}
