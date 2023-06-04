package com.example.seg2105project;

public class Employee extends User  {

    private boolean initialized;

    public Employee(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, "Employee");
        initialized = false;

    }

    //Getters
    public boolean isInitialized(){ return initialized; } //Gets whether or not the the account has been finished

    //Setters
    public void setInitialized(boolean bool){this.initialized = bool;}

}
