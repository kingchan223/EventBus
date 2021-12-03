package Components.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Baby implements Serializable {
    private static final long serialVersionUID = 1;
    public String name;
    public int age;
    public HashMap<String, ArrayList<String>> items = new LinkedHashMap<>();

    public Baby() {}

    public Baby(String name, int age, HashMap<String, ArrayList<String>> items) {
        this.name = name;
        this.age = age;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public HashMap<String, ArrayList<String>> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setItems(HashMap<String, ArrayList<String>> items) {
        this.items = items;
    }
}