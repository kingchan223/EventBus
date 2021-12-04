package Components.entity;
import Utils.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RegisterCourse implements Serializable{

    public static final Long serialVersionUID = Props.E2UID;

    String studentId;
    HashMap<String, ArrayList<String>> preCourseList = new LinkedHashMap<>();

    public RegisterCourse() {}

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

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPreCourseList(HashMap<String, ArrayList<String>> preCourseList) {
        this.preCourseList = preCourseList;
    }
}
