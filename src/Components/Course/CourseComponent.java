/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package Components.Course;

import Components.Props;
import Components.Student.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CourseComponent {
    protected HashMap<String, Course> vCourse;

    public CourseComponent(String sCourseFileName) throws IOException {
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(sCourseFileName));       
        this.vCourse  = new LinkedHashMap<>();
        while (bufferedReader.ready()) {
            String courseInfo = bufferedReader.readLine();
            if(!courseInfo.equals(Props.EMPTY)) this.vCourse.put(courseInfo.split(Props.DIV)[0], new Course(courseInfo));
        }    
        bufferedReader.close();
    }

    public HashMap<String, Course> getCourseList() {
        return this.vCourse;
    }

    public boolean isRegisteredCourse(String courseId) {
        return this.vCourse.get(courseId) != null;
    }
}
