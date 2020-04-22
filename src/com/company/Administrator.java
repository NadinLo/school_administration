package com.company;

import java.sql.*;
import java.util.ArrayList;

public class Administrator extends Person {

    public String getRole() {
        return "ADMINISTRATOR";
    }

    public void createCourse (String name, int max_amount_seats, int teacher_id){
        //check if person.id belongs to a teacher
        boolean isIdCorrect = isIdCorrect(teacher_id);
        if (isIdCorrect){
            //add the course.
                if (dbConnector.editTable("INSERT INTO `course`(`name`, `max_amount_seats`, `teacher_id`) " +
                        "VALUES ('" + name + "'," + max_amount_seats + "," + teacher_id + ")")){
                    System.out.println("Adding the course was successful");
                } else {
                    System.out.println("Adding the course wasn't successful. Please try again");
                }
        } else {
            System.out.println("The chosen id does not belong to a teacher. It's not possible to create this course.");
        }
    }

    private boolean isIdCorrect (int teacher_id){
        ResultSet rs = dbConnector.callUp("SELECT id, first_name, last_name FROM `person` WHERE `role` = 'TEACHER'");
        ArrayList<Integer> teacher_ids = new ArrayList<>();
        try {
            while (rs.next()){
                teacher_ids.add(rs.getInt("id"));
            }
            if (!teacher_ids.contains(teacher_id)){
                return false;
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
        return true;
    }
}
