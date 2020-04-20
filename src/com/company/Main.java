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
        while (loginCheck == false) {
            username = scanner.nextLine();
            System.out.print("password: ");
            password = scanner.nextLine();
            loginCheck = checkLoginData(username, password);
            //System.out.println(loginCheck);
            if (loginCheck == false){
                System.out.println("either your username or password were not correct. Please try again.");
            }
        }
        Person user = login(username, password);
        System.out.println("You are now logged in as " +
                user.getFirst_name() + " " + user.getLast_name() + " (" + user.getRole() + ")");
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
        Person user = new Person();
        try {
            String url = "jdbc:mysql://localhost:3306/school_administration?user=root";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM `person` " +
                    "WHERE `user_name`= '" + username + "' AND `password` = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                user.setId(rs.getInt("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException ex){
            throw new Error("Problem", ex);
        }
        return user;
    }
}
