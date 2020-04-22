package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Teacher extends Person{

    public String getRole() {
        return "TEACHER";
    }

    public void printAllTaughtCourses (int teacherID) {
        ResultSet rs = dbConnector.callUp("SELECT * FROM course WHERE course.teacher_id =" + teacherID);
        if (rs == null) {
            System.out.println("You don't teach anything this year.");
        } else {
            System.out.println("course No | course title       | attendance\n" +
                    "------------------------------------------------");
            try {
                while (rs.next()) {
                    int courseID = rs.getInt("course.id");
                    String courseName = rs.getString("course.name");
                    int maxSeats = rs.getInt("max_amount_seats");
                    int occupiedSeats;
                    ResultSet subRs = dbConnector.callUp("SELECT COUNT(*) FROM `student_course` WHERE course_id = " + courseID);
                    try {
                        if (subRs.next()) {
                            occupiedSeats = subRs.getInt("COUNT(*)");
                            System.out.println((" " + courseID + "          ").substring(0, 10) + "| " +
                                    (courseName + "                    ").substring(0, 19) + "| " +
                                    occupiedSeats + "/" + maxSeats);
                        }
                    } catch (SQLException ex) {
                        System.out.println("could not calculate occupied Seats");
                        ex.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("somehow it's not possible to list all your courses.");
                ex.printStackTrace();
            } finally {
                dbConnector.closeConnection();
            }
        }
    }

    public void printStudentListForCourse (int courseID){
        if (checkTeacher(courseID)){
            ResultSet rs = dbConnector.callUp(
                    "SELECT student_id, CONCAT(person.last_name, ', ', person.first_name) AS 'name', grade " +
                    "FROM student_course " +
                    "INNER JOIN person ON student_course.student_id = person.id " +
                    "WHERE course_id = " + courseID);
            System.out.println("Student ID | Studend's name     | grade\n" +
                    "----------------------------------------------");
            try {
                while (rs.next()){
                    int studentID = rs.getInt("student_id");
                    String studentName = rs.getString("name");
                    String grade = rs.getString("grade");

                    System.out.println((" " + studentID + "          ").substring(0,11) + "|" +
                            (" " + studentName + "              ").substring(0,20) + "| " + grade);
                }
            } catch (SQLException ex){
                System.out.println("could not print the student list");
                ex.printStackTrace();
            } finally {
                dbConnector.closeConnection();
            }
        } else {
            System.out.println("Sorry, you are not allowed to see this list.");
        }

    }

    private boolean checkTeacher (int courseID){
        ResultSet rs = dbConnector.callUp("SELECT teacher_id FROM course WHERE id = " + courseID);
        if (rs == null){
            System.out.println("could not find a course with this ID");
            return false;
        } else {
            try {
                if (rs.next()) {
                    int teacherID = rs.getInt("teacher_id");
                    if (teacherID != this.getId()){
                        System.out.println("You don't teach this course");
                        return false;
                    }
                }
            } catch (SQLException ex){
                System.out.println("couldn't find a teacher's ID");
                ex.printStackTrace();
            } finally {
                dbConnector.closeConnection();
            }
        } return true;
    }

    private boolean checkStudent (int courseID, int studentID){
        ResultSet rs = dbConnector.callUp("SELECT student_id FROM student_course WHERE course_id = " + courseID);
        ArrayList<Integer> students = new ArrayList<>();
        try {
            while (rs.next()){
                students.add(rs.getInt("student_id"));
            }
            if (!students.contains(studentID)){
                return false;
            }
        } catch (SQLException ex){
            System.out.println("couldn't find the course participants or course.");
            ex.printStackTrace();
        } finally {
            dbConnector.closeConnection();
        }
        return true;
    }

    public void grade (int courseID, int studentID, String grade){
        if (checkTeacher(courseID)){
            if (checkStudent(courseID, studentID)){
                //todo: a further check if grade is already given could be possible
                if (dbConnector.editTable("UPDATE student_course SET grade= " + grade +
                        " WHERE course_id = " + courseID + " AND student_id = " + studentID)){
                    System.out.println("Your entry was successful.");
                }
                else {
                    System.out.println("Your entry wasn't successful. Please try again.");
                }
            } else {
                System.out.println("student is not attending this course");
            }
        } else {
            System.out.println("You don't teach this course");
        }
    }
}



