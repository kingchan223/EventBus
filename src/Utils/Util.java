package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
}
