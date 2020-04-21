package com.company;

import java.util.Scanner;

public class AdminProgram {
    public static void start(Administrator admin) {
        Scanner scannerForInt = new Scanner(System.in);
        Scanner scannerForString = new Scanner(System.in);
        System.out.println("You are now logged in as " +
                admin.getFirst_name() + " " + admin.getLast_name() + " (" + admin.getRole() + ")");

        int choice = 0;
        while (true) {
            System.out.println("You have now following possibilities. Enter the number of one option.");
            System.out.println("1) create new courses");
            System.out.println("2) log out");
            choice = scannerForInt.nextInt();
            if (choice == 1) {
                System.out.print("NEW COURSE\n" +
                        "enter the title of the new course: ");
                String name = scannerForString.nextLine();
                System.out.print("enter the maximum number of students to attend: ");
                int maxAmountSeats = scannerForInt.nextInt();
                System.out.print("enter the id of the teacher who's going to take the lesson: ");
                int teacherID = scannerForInt.nextInt();
                admin.createCourse(name, maxAmountSeats, teacherID);
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("wrong number. Please try again.");
            }

        }
    }
}
