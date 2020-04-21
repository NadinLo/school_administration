package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("login: enter username and password!\n" +
                "username: ");
        boolean loginCheck = false;
        String username = null;
        String password = null;
        while (!loginCheck) {
            username = scanner.nextLine();
            System.out.print("password: ");
            password = scanner.nextLine();
            loginCheck = checkLoginData(username, password);
            if (!loginCheck){
                System.out.println("either your username or password were not correct. Please try again.");
            }
        }
        login(username, password);
    }

    private static boolean checkLoginData (String username, String password){
        Connection conn;
        boolean loginCheck = false;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM `person` " +
                    "WHERE `user_name`= '" + username + "' AND `password` = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                loginCheck = true;
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
        return loginCheck;
    }

    private static void login (String username, String password){
        Connection conn;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM `person` " +
                    "WHERE `user_name`= '" + username + "' AND `password` = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                String role = rs.getString("role");
                if (role.equalsIgnoreCase("ADMINISTRATOR")){
                    Administrator user = new Administrator();
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    AdminProgram.start(user);
                }
                else if (role.equalsIgnoreCase("TEACHER")){
                    Teacher user = new Teacher();
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    TeacherProgram.start(user);
                }
                else if (role.equalsIgnoreCase("STUDENT")){
                    Student user = new Student();
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    StudentProgram.start(user);
                }
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
    }
}
