package com.example.seg2105project;



public class Appointment {

    private Clinic c;
    private String date;
    private String time;
    private Patient p;
    private Service s;


    public Appointment(Clinic clinic, String d , String t, Patient patient, Service service){
        c = clinic;
        date = d;
        time = t;
        p = patient;
        s = service;
    }
    public Appointment(){}

    public Clinic getClinic(){
        return c;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public Patient getPatient(){ return p; }

    public Service getService() { return s; }





}
