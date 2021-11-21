package domain;

import Components.Props;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Student {
    private String id;
    private String firstName;
    private String familyName;
    private String department;
    private String compCourseIds;

    public static String makeStudent(String id, String firstName, String familyName, String department, String compCourseIds) {
        return id+ Props.DIV+familyName+ Props.DIV+firstName+ Props.DIV+department+ Props.DIV+compCourseIds;
    }
}
