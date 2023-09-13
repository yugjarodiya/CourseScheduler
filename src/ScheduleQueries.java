/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.sql.Time;
import java.util.Calendar;



/**
 *
 * @author acv
 */
public class ScheduleQueries {
    private static Connection connection;
//    private static PreparedStatement getAllCourses;
    private static PreparedStatement getWaitlisted;
    private static ResultSet  getWaitlistedResultSet;
    private static PreparedStatement updateStudentEntry;

    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;


    private static PreparedStatement getScheduleByStudent;
    private static ResultSet getScheduleByStudentResultSet;

    private static ResultSet getScheduledResults;
    private static PreparedStatement addSchedule;
    
    
    private static PreparedStatement getScheduled;
    
    
    private static ResultSet getScheduleResult;
    private static PreparedStatement getSchedule;

    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?,?,?,?,?)");
            addSchedule.setString(1,entry.getSemester());
            addSchedule.setString(2,entry.getCourseCode());
            addSchedule.setString(3,entry.getStudentID());
            addSchedule.setString(4,entry.getStatus());
            addSchedule.setTimestamp(5,entry.getTimestamp());


            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {


        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {

            getSchedule = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getSchedule.setString(1, semester);
            getSchedule.setString(2, studentID);

            getScheduleResult = getSchedule.executeQuery();
            
            
            
            

            while(getScheduleResult.next())
                
            {
                schedule.add(new ScheduleEntry(getScheduleResult.getString("semester"), getScheduleResult.getString("coursecode"), getScheduleResult.getString("studentid"), getScheduleResult.getString("status"), new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));

               
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return schedule;
    }
    
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getScheduled = connection.prepareStatement("select studentID from app.schedule where semester = ? and coursecode = ?");
            getScheduled.setString(1, currentSemester);
            getScheduled.setString(2, courseCode);
            getScheduledResults = getScheduled.executeQuery();



            while(getScheduledResults.next())
            {
                count++;
            }
        }
            
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }


    
        return count;
    }

    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
    
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> retList = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and courseCode = ? and status = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, courseCode);
            getScheduleByStudent.setString(3, "s");
            getScheduleByStudentResultSet = getScheduleByStudent.executeQuery();
            
            while(getScheduleByStudentResultSet.next())
            {
                retList.add(new ScheduleEntry(semester, courseCode, getScheduleByStudentResultSet.getString(1), "s", getScheduleByStudentResultSet.getTimestamp(2)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return retList;
    };

    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
    
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> retList = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlisted = connection.prepareStatement("select * from app.schedule where semester = ? and courseCode = ? and status = ?");
            getWaitlisted.setString(1, semester);
            getWaitlisted.setString(2, courseCode);
            getWaitlisted.setString(3, "w");

            getWaitlistedResultSet = getWaitlisted.executeQuery();
            
            while(getWaitlistedResultSet.next())
            {

                retList.add(new ScheduleEntry(semester, courseCode, getWaitlistedResultSet.getString("studentid"), "w", getWaitlistedResultSet.getTimestamp("timestamp")));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return retList;
    };
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {

        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? AND studentid = ? AND coursecode = ?");
            
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    };
    
    public static void dropScheduleByCourse(String semester, String courseCode) {

            connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    };
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry)  {

        connection = DBConnection.getConnection();
        try
        {
            updateStudentEntry = connection.prepareStatement("update app.schedule set status = 's' where semester = ? and studentid = ? and coursecode = ?");
            updateStudentEntry.setString(1, semester);
            updateStudentEntry.setString(2, entry.getStudentID());
            updateStudentEntry.setString(3, entry.getCourseCode());
            updateStudentEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    };
    
    
    
    
    
}
