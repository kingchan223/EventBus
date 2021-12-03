/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

public enum EventId {
    ClientOutput,
    QuitTheSystem,

    /*Student*/
    ListStudents,//학생 리스트 바로
    DeleteStudent,//학생 삭제 바로
    RegisterStudent,//Course 에 의해 발동되는 학생등록하기. 들어온 학생정보를 추가한다.
    EnrollCourseByStudent,

    /*Course*/
    ListCourses,//과목 리스트 바로
    DeleteCourse,//강좌 삭제 바로  --> cascade 라는 이름으로 삭제된 강좌를 선수과목으로 담고 있는 강좌들고 추가로 변경하기
    RegisterCourse,//강좌 등록 바로

    CheckCourseInfo,//클라이언트가 학생 추가를 하면 제일 먼저 호출된다. string 에 담긴 강좌의 선수과목을 확인하고  RegisterStudent 를 발생시킨다.
    //NewStudent엔티티에 학생이 들었다고 하는 강좌의 선수과목들을 추가해서 보내준다.
    ValidateCourse,
    ValidateCourses,
}
