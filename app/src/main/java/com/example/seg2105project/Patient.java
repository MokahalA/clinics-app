package com.example.seg2105project;


public class Patient extends User {
    


    public Patient(String firstName, String lastName, String userName, String password ){
        super(firstName,lastName,userName,password,"Patient");
    }

    public Patient(){
        super();
    }

}
