package com.company;

import java.sql.*;

public class Student extends Person {

    public String getRole() {
        return "Student";
    }
    public void printAllCourses () {
        Connection conn;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT course.id, course.name, " +
                    "concat(person.first_name, ' ', person.last_name) as 'teacher', " +
                    "max_amount_seats " +
                    "FROM `course` " +
                    "INNER JOIN person ON course.teacher_id = person.id;";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("course No | course title       | teacher            | free seats\n" +
                    "-----------------------------------------------------------------");
            while (rs.next()){
                int courseID = rs.getInt("course.id");
                String courseName = rs.getString("course.name") + "                    ";
                String teacherName = rs.getString("teacher") + "                    ";
                int maxSeats = rs.getInt("max_amount_seats");
                int freeSeats = maxSeats - getNumberOfOccupiedSeats(courseID);
                String print = courseID + "          ";
                System.out.println("   " + print.substring(0,7) + "| " +
                        courseName.substring(0,19) + "| " + teacherName.substring(0,19) + "| " + freeSeats);
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
    }

    private int getNumberOfOccupiedSeats(int courseID) {
        Connection conn;
        int occupied = 0;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT COUNT(*) FROM `student_course` WHERE course_id = " + courseID;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                occupied = rs.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
        return occupied;
    }
}

//todo: enrol for a course with error/success message
//todo: print list of all attended courses and the result