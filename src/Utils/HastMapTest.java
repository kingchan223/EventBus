package Utils;

import Components.Student.Baby;
import Components.Student.NewStudent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HastMapTest  {

    public static void main(String[] args) throws JsonProcessingException {

        HashMap<String, ArrayList<String>> hashmap = new LinkedHashMap<>();
        String id1 = "12345";
        ArrayList<String> id1Of = new ArrayList<>();
        id1Of.add("12345-1");
        id1Of.add("12345-2");
        id1Of.add("12345-3");
        hashmap.put(id1, id1Of);

        String id2 = "23456";
        ArrayList<String> id2Of = new ArrayList<>();
        id2Of.add("23456-1");
        id2Of.add("23456-2");
        id2Of.add("23456-3");
        hashmap.put(id2, id2Of);

        ArrayList<String> test1 = hashmap.get(id1);
        for (String string : test1) System.out.println(string);

        System.out.println("------------------------");

        hashmap.get(id1).add("12345-4");
        ArrayList<String> test2 = hashmap.get(id1);
        for (String string : test2) System.out.println(string);

        Baby hibaby = new Baby("hi", 25, hashmap);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonHibaby = objectMapper.writeValueAsString(hibaby);
        System.out.println(jsonHibaby);
        Baby baby = objectMapper.readValue(jsonHibaby, Baby.class);

        System.out.println("baby.age:"+baby.age);
        System.out.println("baby.name:"+baby.name);


    }
}
