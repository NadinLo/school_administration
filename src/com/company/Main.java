package com.company;
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static DBConnector dbConnector = new DBConnector("jdbc:mysql://localhost:3306/school_administration?user=root");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String decision = "Y";
        //login possible after every logout
        while (!decision.equalsIgnoreCase("N")) {
            System.out.println("login: enter username and password!");
            boolean loginCheck = false;
            String username = null;
            String password = null;
            for (int i = 0; !loginCheck && i < 3; i++) {
                System.out.print("username: ");
                username = scanner.nextLine();
                System.out.print("password: ");
                password = scanner.nextLine();
                loginCheck = checkLoginData(username, password);
                if (!loginCheck && i < 2) {
                    System.out.println("either your username or password were not correct. Please try again.");
                    System.out.println(i + 1 + "/3 tries");
                } else if (!loginCheck){
                    System.out.println("This was your last try. Please contact the administrator for further steps.");
                    System.exit(0);
                }
            }
            //login and start program
            login(username, password);

            //out of method after logout -> login possible again
            System.out.println("You are logged out again. " +
                    "Do you want to log in again (enter Y or N)?");
            decision = scanner.nextLine();
            if (decision.equalsIgnoreCase("N")) {
                System.out.println("See you soon!");
            }
        }
    }

    private static boolean checkLoginData(String username, String password) {
        boolean loginCheck = false;
        ResultSet rs = dbConnector.callUp("SELECT * FROM `person` " +
                "WHERE `user_name`= '" + username + "' AND `password` = '" + password + "'");
        try {
            if (rs.next()) {
                loginCheck = true;
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
        return loginCheck;
    }

    private static void login(String username, String password) {
        ResultSet rs = dbConnector.callUp("SELECT * FROM `person` " +
                "WHERE `user_name`= '" + username + "' AND `password` = '" + password + "'");
        try {
            if (rs.next()) {
                String role = rs.getString("role");
                if (role.equalsIgnoreCase("ADMINISTRATOR")) {
                    Administrator user = new Administrator();
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    AdminProgram.start(user);
                    //logout after return in method
                    user = null;
                } else if (role.equalsIgnoreCase("TEACHER")) {
                    Teacher user = new Teacher();
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    TeacherProgram.start(user);
                    //logout after return in method
                    user = null;
                } else if (role.equalsIgnoreCase("STUDENT")) {
                    Student user = new Student();
                    user.setId(rs.getInt("id"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    StudentProgram.start(user);
                    //logout after return in method
                    user = null;
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        } finally {
            dbConnector.closeConnection();
        }
    }
}
