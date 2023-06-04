package com.example.seg2105project;

// Class that will be used to create User objects


public class User {

    //Instance variables
    private String firstName;     // User's first name
    private String lastName;     //  User's last name
    private String username;     //Credential used to login
    private String password;     // Password used to login
    private String type;        // User type (Either "Admin", "Employee", or "Patient")

    //Constructor
    public User(String firstName, String lastName, String username, String password, String type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password; //The hashed byte[] is going to be a string when it is input by the user
        this.type = type;
    }

    //No arg constructor needed for the AccountList adapter class to work
    public User(){}

    //Getter methods

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getUserName(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getType() { return type; }


    //Setter methods

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setType(String type){ this.type = type; }

}
