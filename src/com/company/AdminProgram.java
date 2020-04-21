package com.company;

public class AdminProgram {
    public static void start (Administrator admin){
        System.out.println("You are now logged in as " +
                admin.getFirst_name() + " " + admin.getLast_name() + " (" + admin.getRole() + ")");

    }
}
