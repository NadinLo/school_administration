package com.company;

import java.util.Scanner;

public class TeacherProgram {
    public static void start(Teacher teacher) {
        Scanner scannerForInt = new Scanner(System.in);
        Scanner scannerForString = new Scanner(System.in);

        System.out.println("You are now logged in as " +
                teacher.getFirst_name() + " " + teacher.getLast_name() + " (" + teacher.getRole() + ")");

        int choice;
        while (true) {
            System.out.println("\nYou have now following possibilities. Enter the number of one option.");
            System.out.println("1) print list of all courses which are taught by oneself");
            System.out.println("2) print list of students who attend to a course you teach");
            System.out.println("3) give a grade to a student for a course you teach and he*she attends to");
            System.out.println("4) log out");

            choice = scannerForInt.nextInt();
            if (choice == 1) {
                System.out.println("LIST OF ALL COURSES: ");
                teacher.printAllTaughtCourses(teacher.getId());
            } else if (choice == 2) {
                System.out.println("Please enter the number of the course you want to see the students list");
                teacher.printStudentListForCourse(scannerForInt.nextInt());
            } else if (choice == 3) {
                System.out.println("Please enter the student's id and the number of the course you want to grade.");
                System.out.print("student's id: "); int studentID = scannerForInt.nextInt();
                System.out.print("course number: "); int courseID = scannerForInt.nextInt();
                System.out.println("How do you want to grade the student's achievement? (1 (very good) to 6 (failed)");
                //grade is String because it should be possible to upgrade like 3+ or 2- or use the american system with letters
                String grade = scannerForString.nextLine();
                teacher.grade(courseID, studentID, grade);

            } else if (choice == 4) {
                break;
            } else {
                System.out.println("wrong number. Please try again.");
            }
        }
    }
}
