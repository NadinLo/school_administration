package com.company;

public class StudentProgram {
    public static void start (Student student){
        System.out.println("You are now logged in as " +
                student.getFirst_name() + " " + student.getLast_name() + " (" + student.getRole() + ")");
    }
}
