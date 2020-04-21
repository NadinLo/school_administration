package com.company;

import java.sql.*;
import java.util.ArrayList;

public class Administrator extends Person {

    public String getRole() {
        return "ADMINISTRATOR";
    }

    public void createCourse (String name, int max_amount_seats, int teacher_id){
        Connection conn;
        boolean isIdCorrect = isIdCorrect(teacher_id);
        if (isIdCorrect){
            try {
                String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
                conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                String command = "INSERT INTO `course`(`name`, `max_amount_seats`, `teacher_id`) " +
                        "VALUES ('" + name + "'," + max_amount_seats + "," + teacher_id + ")";
                stmt.executeUpdate(command);
            } catch (SQLException ex){
                throw new Error("Problem", ex);
            }
        } else {
            System.out.println("The chosen id does not belong to a teacher. It's not possible to create this course.");
        }
    }

    private boolean isIdCorrect (int teacher_id){
        Connection conn;
        boolean isIdCorrect = false;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT id, first_name, last_name FROM `person` WHERE `role` = 'TEACHER'";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Integer> teacher_ids = new ArrayList<>();
            while (rs.next()){
                teacher_ids.add(rs.getInt("id"));
            }
            if (teacher_ids.contains(teacher_id)){
                isIdCorrect = true;
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
        return isIdCorrect;
    }
}
