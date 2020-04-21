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

    public void signInCourse (int courseID){
        int occupiedSeats = getNumberOfOccupiedSeats(courseID);
        int maxSeats = 0;
        Connection conn;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT max_amount_seats FROM `course` WHERE id = " + courseID;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                maxSeats = rs.getInt("max_amount_seats");
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
        if (occupiedSeats < maxSeats){
            try {
                String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
                conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                String command = "INSERT INTO `student_course`(`course_id`, `student_id`) " +
                        "VALUES (" + courseID + "," + this.getId() + ")";
                stmt.executeUpdate(command);
                System.out.println("You've successfully signed in.");
            } catch (SQLException ex){
                throw new Error("Problem", ex);
            }
        } else {
            System.out.println("Sorry, this course ist already full. You cannot sign in.");
        }
    }
}

//todo: print list of all attended courses and the result