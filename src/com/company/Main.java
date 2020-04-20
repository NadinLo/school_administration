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
        Person user = login(username, password);
        System.out.println("You are now logged in as " +
                user.getFirst_name() + " " + user.getLast_name() + " (" + user.getClass().getSimpleName() + ")");
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

    private static Person login (String username, String password){
        Connection conn;
        Person user = null;
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM `person` " +
                    "WHERE `user_name`= '" + username + "' AND `password` = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                String role = rs.getString("role");
                if (role.equalsIgnoreCase("ADMINISTRATOR")){
                    user = new Administrator();
                }
                else if (role.equalsIgnoreCase("TEACHER")){
                    user = new Teacher();
                }
                else if (role.equalsIgnoreCase("STUDENT")){
                    user = new Student();
                }
                if (user != null) {
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                }
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
        return user;
    }
}
