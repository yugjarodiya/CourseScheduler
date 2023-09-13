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

/**
 *
 * @author acv
 */
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllCourses;
//    private static PreparedStatement getAllCourses;

    private static PreparedStatement getSemesterList;
    private static PreparedStatement addCourse;

    private static ResultSet allCourseResults;
    private static PreparedStatement getCourseCodesList;
    private static ResultSet courseCodeResultSet;
    
    private static ResultSet getSeatsResults;
    private static PreparedStatement getSeats;
    
    private static PreparedStatement delCourse;

    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getAllCourses = connection.prepareStatement("select * from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            allCourseResults = getAllCourses.executeQuery();
            
            while(allCourseResults.next())
            {
                courses.add(new CourseEntry(allCourseResults.getString("semester"),allCourseResults.getString("coursecode"),allCourseResults.getString("description"),allCourseResults.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, courseCode, description, seats) values (?,?,?,?)");
            addCourse.setString(1,course.getSemester());
            addCourse.setString(2,course.getCourseCode());
            addCourse.setString(3,course.getCourseDescription());
            addCourse.setInt(4,course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    
    //DONE
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getCourseCodesList = connection.prepareStatement("select coursecode from app.course where semester = (?)");
            getCourseCodesList.setString(1,semester);

            courseCodeResultSet = getCourseCodesList.executeQuery();
            
            while(courseCodeResultSet.next())
            {
                courseCodes.add(courseCodeResultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
    public static int getCourseSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int value = 0;
        try
        {
            getSeats = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getSeats.setString(1,semester);
            getSeats.setString(2,courseCode);
            getSeatsResults = getSeats.executeQuery();

            
              while(getSeatsResults.next())
                value = getSeatsResults.getInt(1);
            
            

            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return value;
        
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try
        {
            delCourse = connection.prepareStatement("delete from app.course where semester = ? and coursecode = ?");
            delCourse.setString(1, semester);
            delCourse.setString(2, courseCode);
            delCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    
    }
    
    
}
