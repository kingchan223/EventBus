package domain;

import Components.Props;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Course {
    private String id;
    private String name;
    private String profFamilyName;
    private String preCourseIds;

    public static String makeCourse(String id, String name, String profFamilyName, String preCourseIds) {
        return id+ Props.DIV+name+ Props.DIV+profFamilyName+ Props.DIV+preCourseIds;
    }
}
