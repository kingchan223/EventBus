package Utils;

import Components.InputType;
import Components.Props;
import Components.Student.StudentComponent;
import Framework.Event;
import Framework.EventId;
import Framework.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

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

    private static boolean isValidStr(String str) {
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

    public static void printWrongInputMsg(InputType iputType){
        System.out.println(Props.WRONG_STR_INPUT+ iputType);
    }

    public String validateCourseIds(String courseIds) {//12345 43243 84843
        String[] ids = courseIds.split(Props.DIV);
        String retIds = Props.EMPTY;
        for (String id : ids) {
            String nId = validateId(id, Props.COURSE_ID_LEN);
            retIds += nId + Props.DIV;
        }
        return retIds;
    }


//    public static void studentMethods(Event event, StudentComponent studentsList) throws RemoteException {
//        printLogEvent(event);
//        switch (event.getMethod()) {
//            case CREATE -> sendEvent(EventId.ClientOutput, registerStudent(studentsList, event.getMessage()), Method.CREATE);
//            case READ -> sendEvent(EventId.ClientOutput, makeStudentList(studentsList), Method.READ);
//            case DELETE -> sendEvent(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage()), Method.DELETE);
//            default -> {}
//        }
//    }
//
//    public static void sendEvent(EventId eventId, String text, Method method) throws RemoteException {
//        eventBus.sendEvent(new Event(eventId, text, method));
//    }
//
//    public static void sendEventQuit(EventId eventId, String text, long componentId) throws RemoteException {
//        eventBus.sendEvent(new Event(eventId, text, null));
//        eventBus.unRegister(componentId);
//    }
//
//    private static void printLogEvent(Event event) {
//        System.out.println(event.getMethod() + Props.EVENT_INFO_ID + event.getEventId() + Props.EVENT_INFO_MSG + event.getMessage());
//    }

//    public static void printUserInput(String userInput){
//        System.out.println("\n ** Message: " + userInput + "\n");
//    }
//
//    public static void printInvalidVal(String id){
//        System.out.println("\nThe id ( "+id+" ) you entered is invalid.");
//    }
}
