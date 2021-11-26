package Utils;


public class EntityUtil {

    public boolean validatePreCourse(String studentStr, String message) {
        String[] studentSplit = studentStr.split(Props.DIV);
        String[] messageSplit = message.split(Props.DIV);
        String[] courseSplit = new String[messageSplit.length-1];
        System.arraycopy(messageSplit, 1, courseSplit, 0, messageSplit.length - 1);


        for(int i=Props.PRECOURSE_IDX; i< courseSplit.length; i++){
            boolean has = false;
            for(int j=Props.COMPCOURSE_IDX; j<studentSplit.length; j++)
                if(courseSplit[i].equals(studentSplit[j])) has = true;
            if(!has) return false;
        }
        return true;
    }
}
