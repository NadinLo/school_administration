package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher extends Person{
    private DBConnector dbConnector = new DBConnector("jdbc:mysql://localhost:3306/school_administration?user=root");

    public String getRole() {
        return "TEACHER";
    }
    public void printAllTaughtCourses (int teacherID){
        ResultSet rs = dbConnector.callUp ("SELECT * FROM course WHERE course.teacher_id =" + teacherID);
        if (rs == null){
            System.out.println("You don't teach anything this year.");
        }
        System.out.println("course No | course title       | attendance\n" +
                "------------------------------------------------");
        try {
            while (rs.next()) {
                int courseID = rs.getInt("course.id");
                String courseName = rs.getString("course.name");
                int maxSeats = rs.getInt("max_amount_seats");
                int occupiedSeats = 0;
                ResultSet subRs = dbConnector.callUp("SELECT COUNT(*) FROM `student_course` WHERE course_id = " + courseID);
                try {
                    if (subRs.next()) {
                        occupiedSeats = subRs.getInt("COUNT(*)");
                        System.out.println((" " + courseID + "          ").substring(0,10) + "| " +
                                (courseName + "                    ").substring(0,19) + "| " +
                                occupiedSeats + "/" + maxSeats);
                    }
                } catch (SQLException ex){
                    System.out.println("could not calculate occupied Seats");
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex){
            System.out.println("somehow it's not possible to list all your courses.");
            ex.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }


    }
}

//todo: print list of students who attend to course in case the user is the teacher
//todo: give a grade to a student for a course he*she attends to
