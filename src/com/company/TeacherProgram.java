package com.company;

public class TeacherProgram {
    public static void start (Teacher teacher){
        System.out.println("You are now logged in as " +
                teacher.getFirst_name() + " " + teacher.getLast_name() + " (" + teacher.getRole() + ")");
    }
}
