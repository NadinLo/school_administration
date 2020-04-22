package com.company;

import java.sql.*;
import java.util.ArrayList;

public class Student extends Person {

    public String getRole() {
        return "Student";
    }

    public void printAllCourses() {

        ResultSet rs = dbConnector.callUp("SELECT course.id, course.name, " +
                "concat(person.first_name, ' ', person.last_name) as 'teacher', " +
                "max_amount_seats " +
                "FROM `course` " +
                "INNER JOIN person ON course.teacher_id = person.id;");
        System.out.println("course No | course title                                      | teacher            | free seats\n" +
                "-----------------------------------------------------------------------------------------------");
        try {
            while (rs.next()) {
                int courseID = rs.getInt("course.id");
                String courseName = rs.getString("course.name") + "                                    ";
                String teacherName = rs.getString("teacher") + "                    ";
                int maxSeats = rs.getInt("max_amount_seats");
                int freeSeats = maxSeats - getNumberOfOccupiedSeats(courseID);
                String print = courseID + "          ";
                System.out.println("   " + print.substring(0, 7) + "| " +
                        courseName.substring(0, 50) + "| " + teacherName.substring(0, 19) + "| " + freeSeats);
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
        dbConnector.closeConnection();
    }

    private int getNumberOfOccupiedSeats(int courseID) {
        int occupied = 0;
        ResultSet rs = dbConnector.callUp("SELECT COUNT(*) FROM `student_course` WHERE course_id = " + courseID);
        try {
            if (rs.next()) {
                occupied = rs.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
        return occupied;
    }

    public void signInCourse(int courseID) {
        //check courseID - is ID valid?
        if (checkCourseID(courseID)) {
            //is student already signed in?
            if (checkStudentCourse(courseID)) {
                //get no of maximum seats and number of already occupied seats
                int occupiedSeats = getNumberOfOccupiedSeats(courseID);
                int maxSeats = getNumberOfMaxSeats(courseID);
                if (occupiedSeats < maxSeats) {
                    if (dbConnector.editTable("INSERT INTO `student_course`(`course_id`, `student_id`) " +
                            "VALUES (" + courseID + "," + this.getId() + ")")) {
                        System.out.println("You've successfully signed in.");
                    } else {
                        System.out.println("You couldn't sign in. Please try again.");
                    }
                } else {
                    System.out.println("Sorry, this course is already full. You cannot sign in.");
                }
            }
        }
    }

    public void printAttendedCourses(int studentID) {
        ResultSet rs = dbConnector.callUp("SELECT course.id, course.name, student_course.grade " +
                "FROM student_course " +
                "INNER JOIN course ON student_course.course_id = course.id " +
                "WHERE student_id = " + studentID);
        System.out.println("course No | course title                                      | teacher            | grade\n" +
                "-----------------------------------------------------------------------------------------------");
        try {
            while (rs.next()) {
                int courseID = rs.getInt("course.id");
                String courseName = rs.getString("course.name") + "                                            ";
                String teacherName = getTeacherName(courseID) + "                    ";
                String grade = rs.getString("student_course.grade");
                String print = courseID + "          ";
                System.out.println("   " + print.substring(0, 7) + "| " +
                        courseName.substring(0, 50) + "| " + teacherName.substring(0, 19) + "| " + grade);
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
    }

    private String getTeacherName(int courseID) {
        String teacherName = null;
        ResultSet rs = dbConnector.callUp("SELECT concat(person.first_name, ' ', person.last_name) as 'teacher'" +
                "FROM `course` " +
                "INNER JOIN person ON course.teacher_id = person.id " +
                "WHERE course.id = " + courseID);
        try {
            if (rs.next()) {
                teacherName = rs.getString("teacher");
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
        return teacherName;
    }

    private int getNumberOfMaxSeats (int courseID){
        ResultSet rs = dbConnector.callUp("SELECT max_amount_seats FROM `course` WHERE id = " + courseID);
        int maxSeats = 0;
        try {
            if (rs.next()) {
                maxSeats = rs.getInt("max_amount_seats");
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
        return maxSeats;
    }

    private boolean checkStudentCourse (int courseID){
        ResultSet rs = dbConnector.callUp("SELECT student_id FROM student_course WHERE course_id = " + courseID);
        ArrayList<Integer> students = new ArrayList<>();
        try {
            while (rs.next()){
                students.add(rs.getInt("student_id"));
            }
        } catch (SQLException ex){
            System.out.println("Problem");
            ex.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        if (students.contains(this.getId())){
            System.out.println("You are already signed in for this course.");
            return false;
        }
        return true;
    }

    private boolean checkCourseID (int courseID) {
        ResultSet rs = dbConnector.callUp("SELECT * FROM `course` WHERE course.id = " + courseID);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Problem");
            ex.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return false;
    }
}