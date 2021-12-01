package Utils;


import Components.Course.CourseComponent;

import java.io.IOException;

public class EntityUtil {

    public boolean validatePreCourse(String studentStr, String message) {
        CourseComponent courseComponent = null;
        try {
            courseComponent = new CourseComponent(Props.COURSES_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] studentSplit = studentStr.split(Props.DIV);
        String[] messageSplit = message.split(Props.DIV);
        String[] courseSplit = new String[messageSplit.length-1];
        System.arraycopy(messageSplit, 1, courseSplit, 0, messageSplit.length - 1);

        for(int i=Props.PRECOURSE_IDX; i< courseSplit.length; i++){
            boolean has = false;
            for(int j=Props.COMPCOURSE_IDX; j<studentSplit.length; j++){
                if(!(courseComponent.isRegisteredCourse(courseSplit[i])&&courseComponent.isRegisteredCourse(studentSplit[j]))) return false;
                if (courseSplit[i].equals(studentSplit[j])) has = true;
            }
            if(!has) return false;
        }
        return true;
    }

    public boolean validatePreCourse(String studentStr){
        String[] studentSplit = studentStr.split(Props.DIV);
        String message = Props.EMPTY;
        for (int i = 3; i < studentSplit.length; i++) message += Props.DIV + studentSplit[i];
        return validatePreCourse(studentStr, message);
    }

    public String makeCourseStr(String id, String name, String profFamilyName, String preCourseIds) {
        return id+ Props.DIV+name+ Props.DIV+profFamilyName+ Props.DIV+preCourseIds;
    }

    public String makeStudentStr(String id, String firstName, String familyName, String department, String compCourseIds) {
        return id+ Props.DIV+familyName+ Props.DIV+firstName+ Props.DIV+department+ Props.DIV+compCourseIds;
    }
}
