package com.example.seg2105project;

public class  Service {

    private String name;
    private String role;
    private String id;
    private double rate;

    public Service(String id, String serviceName, String role, double rate){
        this.id = id;
        name = serviceName;
        this.role = role;
        this.rate = rate;
    }
    public Service(){}

    public String getId(){return  this.id;}

    public String getName(){
        return this.name;
    }

    public String getRole(){
        return this.role;
    }

    public double getRate() { return this.rate; }

    public void setId(String i){ id = i;}

    public void setName(String n){ name = n; }

    public void setRole(String p) { role = p; }

    public void setRate(double r) { rate = r; }



}
