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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSemester;
    private static PreparedStatement getSemesterList;
    private static ResultSet resultSet;
    
    private static ResultSet addStudentResults;
    private static PreparedStatement addStudent;
    
    private static ResultSet getAllStudentsResults;
    private static PreparedStatement getAllStudents;
    
    private static ResultSet getStudentIDResults;
    private static PreparedStatement getStudentID;
    
    private static ResultSet tr;
    private static PreparedStatement t;
    
    private static PreparedStatement getStudentByID;
    private static ResultSet getStudentByIDResult;
    
    private static PreparedStatement delStudent;
    private static ResultSet delStudentResult;
    
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1,student.getStudentID());
            addStudent.setString(2,student.getFirstName());
            addStudent.setString(3,student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student order by studentid");
            getAllStudentsResults = getAllStudents.executeQuery();
            
            while(getAllStudentsResults.next())
            {
                students.add(new StudentEntry(getAllStudentsResults.getString("studentid"),getAllStudentsResults.getString("firstname"),getAllStudentsResults.getString("lastname")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
        
    }
    public static String getStudentID(String lastname, String firstname)
    {
        connection = DBConnection.getConnection();
        String id = "";
        try
        {
            getStudentID = connection.prepareStatement("select studentid from app.student where (lastname,firstname) = (?,?)");
            getStudentIDResults = getStudentID.executeQuery();
            
            while(getStudentIDResults.next())
            {
                id = getStudentIDResults.getString("studentid");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return id;
        
        
    }
    
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            getAllStudents = connection.prepareStatement("select studentid, firstname, lastname from app.student where studentid = ?");
            getAllStudents.setString(1, studentID);
            resultSet = getAllStudents.executeQuery();
            
            resultSet.next();
            return new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            return null;
        }
    
    
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try
        {
            delStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            delStudent.setString(1, studentID);
            delStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    
        
        
    }
    
    public static ArrayList<StudentEntry> getOneStudent(String firstname, String lastname){
  
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            t = connection.prepareStatement("select * from app.student where firstname = ? and lastname = ?");
            t.setString(1, firstname);
            t.setString(2, lastname);

            tr = t.executeQuery();
            
            while(tr.next())
            {
                student.add(new StudentEntry(tr.getString("studentid"),tr.getString("firstname"),tr.getString("lastname")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    
    }
    
    
}
