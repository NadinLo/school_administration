package com.company;

import java.util.Scanner;

public class TeacherProgram {
    public static void start(Teacher teacher) {
        Scanner scannerForInt = new Scanner(System.in);

        System.out.println("You are now logged in as " +
                teacher.getFirst_name() + " " + teacher.getLast_name() + " (" + teacher.getRole() + ")");

        int choice;
        while (true) {
            System.out.println("\nYou have now following possibilities. Enter the number of one option.");
            System.out.println("1) print list of all courses which are taught by oneself");
            System.out.println("2) ");
            System.out.println("3) ");
            System.out.println("4) log out");

            choice = scannerForInt.nextInt();
            if (choice == 1) {
                System.out.println("LIST OF ALL COURSES: ");
                teacher.printAllTaughtCourses(teacher.getId());
            } else if (choice == 2) {

            } else if (choice == 3) {

            } else if (choice == 4) {
                break;
            } else {
                System.out.println("wrong number. Please try again.");
            }
        }
    }
}
