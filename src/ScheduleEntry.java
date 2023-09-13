

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.sql.Timestamp;



/**
 *
 * @author acv
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp;
            
    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timestamp){
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
//        this.timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());


    }
    
    public String getSemester(){
        return semester;
    }
    public String getCourseCode(){
        return courseCode;
    }
    public String getStudentID(){
        return studentID;
    }
    public String getStatus(){
        return status;
    }
    public Timestamp getTimestamp(){
        return timestamp;
    }


    
   
    
}
