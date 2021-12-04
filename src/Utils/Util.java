package Utils;

import Components.ClientInput.ClientInputMain.InputType;
import Components.Course.CourseComponent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    public String validateStr(String str, InputType inputType ){
        if(isValidStr(str)) return str;
        String reStr = str;
        while(!isValidStr(reStr)){
            printWrongInputMsg(inputType);
            try {
                reStr = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reStr;
    }

    private boolean isValidStr(String str) {
        return str != null && !str.equals(Props.DIV) && !str.equals(Props.EMPTY);
    }

    private static boolean isNumber(String id){
        try{
            Integer.parseInt(id);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public String validateId(String id, int idLen){
        if(isNumber(id) && id.length() == idLen) return id;
        String reId = id;
        while(!isNumber(reId) || reId.length() != idLen){
            Props.printInvalidVal(reId);
            printWrongIdMsg(idLen);
            try {
                reId = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reId;
    }

    public void printWrongIdMsg(int idLen){
        if(idLen == Props.STD_ID_LEN) System.out.println(Props.WRONG_STD_ID_INPUT);
        if(idLen == Props.COURSE_ID_LEN) System.out.println(Props.WRONG_COURSE_ID_INPUT);
    }

    public void printWrongInputMsg(InputType iputType){
        System.out.println(Props.WRONG_STR_INPUT+ iputType);
    }

    public String validateCourseIds(String courseIds) {//12345 43243 84843
        if(!isValidStr(courseIds.trim())) return Props.EMPTY;
        String[] ids = courseIds.split(Props.DIV);
        String retIds = Props.EMPTY;
        for (String id : ids) {
            String nId = validateId(id, Props.COURSE_ID_LEN);
            retIds += nId + Props.DIV;
        }
        return retIds;
    }

    //Full string 학생 정보에서 과목만 뽑아낸다.
    public ArrayList<String> extCourseInfoFrom(String message) {
        String[] split = message.split(Props.DIV);
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.addAll(Arrays.asList(split).subList(Props.COMPCOURSE_IDX, split.length));
        return retVal;
    }

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
