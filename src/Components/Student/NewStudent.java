package Components.Student;

import Components.Student.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class NewStudent extends Student implements Serializable{

    HashMap<String, ArrayList<String>> preCourseList = new LinkedHashMap<>();



    public NewStudent(String inputString){
        super(inputString);
    }

    public NewStudent(String inputString, String sign) {
        super(inputString, sign);
    }

    public void addPreCourseOf(String courseId, String preCourseId){
        if(!this.preCourseList.containsKey(courseId)) preCourseList.get(courseId).add(preCourseId);
    }

    public void addPreCourseOf(String courseId, ArrayList<String> preCourseId){
        ArrayList<String> course = preCourseList.get(courseId);
        for (String id : preCourseId)
            if(!this.preCourseList.containsKey(courseId))course.add(id);
    }
}
