package Utils;

public interface Props {
    public static final String DIV = " ";
    public static final String EMPTY = "";
    public static final String ENTER = "\n";
    public static final int STD_ID_LEN = 8;
    public static final int COURSE_ID_LEN = 5;
    public static final int EQ_CAPA = 15;
    public static final int EQ_INCRE = 1;
    public static final int PORT = 8088;
    public static final String HOST = "127.0.0.1";
    public static final int CLOCK_TIME = 1000;
    public static final int PRECOURSE_IDX = 3;
    public static final int COMPCOURSE_IDX = 4;
    public static final Long UID = 1L;
    public static final String LOOKUP = "EventBus";
    public static final String COURSES_TXT = "src/Courses.txt";
    public static final String STUDENTS_TXT = "src/Students.txt";
    public static final String GET = "Get";
    public static final String POST = "Post";
    public static final String PUT = "Put";
    public static final String OK = "OK12345";



    public static final String STD_ID_MSG = "\nEnter student ID and press return (Ex. 20131234)>> ";
    public static final String STD_FMNAME_MSG = "\nEnter family name and press return (Ex. Hong)>> ";
    public static final String STD_FNAME_MSG = "\nEnter first name and press return (Ex. Gildong)>> ";
    public static final String STD_DEPART_MSG = "\nEnter department and press return (Ex. CS)>> ";
    public static final String STD_COMP_COURSE_MSG = "\nEnter a list of IDs (put a space between two different IDs) of the completed courses and press return >> \n(Ex. 17651 17652 17653 17654)";

    public static final String COURSE_ID_MSG = "\nEnter course ID and press return (Ex. 12345)>> ";
    public static final String COURSE_P_FMNAME_MSG = "\nEnter the family name of the instructor and press return (Ex. Hong)>> ";
    public static final String COURSE_NAME_MSG = "\nEnter the name of the course ( substitute a space with ab underbar(_) ) and press return (Ex. C++_Programming)>> ";
    public static final String COURSE_PRECOURSE_MSG = "\nEnter a list of IDs (put a space between two different IDs) of prerequisite courses and press return >> \n(Ex. 12345 17651)";

    public static final String WRONG_STD_ID_INPUT = "\nYou entered the wrong ID. Please enter it in 8 digits. ( Ex.20211234 )>>";
    public static final String WRONG_COURSE_ID_INPUT = "\nYou entered the wrong ID. Please enter it in 5 digits. ( Ex.17899 )>>";
    public static final String WRONG_STR_INPUT = "\nYou entered is wrong. Please enter again. >>";

    public static final String MENU1 = "1. List Students";
    public static final String MENU_N1 = "1";
    public static final String MENU2 = "2. List Courses";
    public static final String MENU_N2 = "2";
    public static final String MENU3 = "3. Register a new Student";
    public static final String MENU_N3 = "3";
    public static final String MENU4 = "4. Register a new Course";
    public static final String MENU_N4 = "4";
    public static final String MENU5 = "5. Delete a Student";
    public static final String MENU_N5 = "5";
    public static final String MENU6 = "6. Delete a Course";
    public static final String MENU_N6 = "6";
    public static final String MENU7 = "7. Student Course Enrolment";
    public static final String MENU_N7 = "7";
    public static final String MENU0 = "0. Quit the system";
    public static final String MENU_N0 = "0";
    public static final String MENU_C = "\n Choose No.: ";
    public static final String SEND_E = "\n** Sending an event ID:";
    public static final String SELECT_AGAIN = "Please select menu again.";

    public static final String BUS_RUNNING = "Event Bus is running now...";
    public static final String BUS_ERR = "Event bus startup error: ";
    public static final String COMPONENT_REGI = "Component is registered... ID: ";
    public static final String COMPONENT_UNREGI = "Component is unregistered... ID: ";
    public static final String EVENT_INFO_ID = "\nEvent Inforamtion ID: ";
    public static final String EVENT_INFO_MSG = "\nEvent Message : ";
    public static final String QUIT_SYS = "Quit the system!!!";
    public static final String CLIENT_INPUT_SUCCESS = "** ClientInputMain is successfully registered. ID: ";
    public static final String CLIENT_OUTPUT_SUCCESS = "** ClientOutputMain is successfully registered. ID: ";
    public static final String COURSE_MAIN_SUCCESS = "** CourseMain is successfully registered. ID: ";
    public static final String STUDENT_MAIN_SUCCESS = "** StudentMain is successfully registered. ID: ";
    public static final String STD_DELETED = "This student is successfully deleted.";
    public static final String STD_NOT_REGI = "This student is not registered.";
    public static final String STD_ADD = "This student is successfully added.";
    public static final String STD_ALREADY_REGI = "This student is already registered.";
    public static final String COURSE_DELETED = "This course is successfully deleted.";
    public static final String COURSE_NOT_REGI = "This course is not registered.";
    public static final String COURSE_NOT_VALID = "This course is not registered OR pre-course is not enough.";
    public static final String COURSE_ADD = "This course is successfully added.";
    public static final String COURSE_ALREADY_REGI = "This course is already registered.";
    public static final String PRE_NOT_ENOUGH = "This student's PreCourse is not enough.";
    public static final String STD_UPDATED = "This student succeeded in registering for the course.";
    public static final String STD_ALREADY_COMP = "This student try register already completed course";
    public static final String STD_CHECK_COURSE = "This student's courses go to Course Server";

    static void printUserInput(String userInput) {
        System.out.println("\n ** Message: " + userInput + "\n");
    }

    static void printInvalidVal(String id) {
        System.out.println("\nThe id ( " + id + " ) you entered is invalid.");
    }
}
