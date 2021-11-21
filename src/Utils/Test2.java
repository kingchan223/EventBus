package Utils;

import Components.InputType;
import Components.Props;
import domain.Course;
import domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Test2 {
    public static void main(String[] args) {
        String[] strList = {"88888888","17651", "Kim", "Models_of_Software_Systems", "12345"};
        String[] strList2 = new String[strList.length-1];
        System.arraycopy( strList, 1, strList2, 0, strList.length-1 );
        for (String s : strList2)
            System.out.println( s);
    }
}
