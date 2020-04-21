package com.company;

import java.util.Scanner;

public class StudentProgram {
    public static void start (Student student){
        Scanner scannerForInt = new Scanner(System.in);
        Scanner scannerForString = new Scanner(System.in);
        System.out.println("You are now logged in as " +
                student.getFirst_name() + " " + student.getLast_name() + " (" + student.getRole() + ")");

        int choice;
        while (true) {
            System.out.println("\nYou have now following possibilities. Enter the number of one option.");
            System.out.println("1) print a list of all courses");
            System.out.println("2) sign in for a course");
            System.out.println("3) print list of all attended courses incl. the result");
            System.out.println("4) log out");
            choice = scannerForInt.nextInt();
            if (choice == 1) {
                System.out.println("LIST OF ALL COURSES: ");
                student.printAllCourses();
            } else if (choice == 2) {
                System.out.println("Which course do you want to choose? Enter the course number.");
                int courseID = scannerForInt.nextInt();
                System.out.println("SIGN IN");
                student.signInCourse(courseID);
            } else if (choice == 3) {
                System.out.println("YOUR COURSES: ");
                student.printAttendedCourses(student.getId());
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("wrong number. Please try again.");
            }

        }
    }
}
