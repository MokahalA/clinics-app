package com.example.seg2105project;

import java.util.ArrayList;

public class Clinic {

    private String address, name;
    private String phoneNum;
    private String[] monHours, tueHours, wenHours, thuHours, friHours, satHours, sunHours;
    private boolean credit, cash, debit, oHIP, pubIns, priIns;
    private ArrayList<Service> servicesOffered;
    private ArrayList<String> checkedInList;
    private int wait;
    private double rate;
    private int review_num;

    public Clinic (String name, String address, String phoneNum, boolean credit, boolean cash, boolean debit, boolean ohip, boolean pubIns, boolean priIns, int wait, double rate){
        this.credit = credit;
        this.cash = cash;
        this.debit = debit;

        this.oHIP = ohip;
        this.pubIns = pubIns;
        this.priIns= priIns;

        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;

        this.wait = wait;
        this.rate =  rate;
        this.review_num = 1;

        //Initially clinics will be open all day
        monHours = new String[]{"00:00", "24:00"};

        tueHours = new String[]{"00:00", "24:00"};

        wenHours = new String[]{"00:00", "24:00"};

        thuHours = new String[]{"00:00", "24:00"};

        friHours = new String[]{"00:00", "24:00"};

        satHours = new String[]{"00:00", "24:00"};

        sunHours = new String[]{"00:00", "24:00"};

        servicesOffered = new ArrayList<>();
        checkedInList = new ArrayList<>();

    }

    //No argument constructor
    public Clinic(){
        checkedInList = new ArrayList<>();

    }


    //Getters
    public String getName(){ return name; }
    public String getAddress() { return address; }
    public String getPhoneNum() { return phoneNum; }

    public boolean getCredit() { return credit; }
    public boolean getDebit() { return debit; }
    public boolean getCash() { return cash; }

    public boolean getOHIP() { return oHIP; }
    public boolean getPubIns() { return pubIns; }
    public boolean getPriIns() { return priIns; }

    public ArrayList<Service> getServicesOffered(){return this.servicesOffered;}

    public int getWait() { return wait; }


    //Setters

    public void setName(String n){this.name=n;}
    public void setAddress(String n){this.address = n;}
    public void setPhoneNum(String num){this.phoneNum = num;}

    public void setCredit(boolean value){this.credit = value;}
    public void setDebit(boolean value) {this.debit = value;}
    public void setCash(boolean value) {this.cash = value;}

    public void setOHIP(boolean value) {this.oHIP = value;}
    public void setPubIns(boolean value) {this.pubIns = value;}
    public void setPriIns(boolean value) {this.priIns = value;}

    public void setWait(int wait){this.wait = wait;}

    //This functions is used to set time
    public void setHours(String[] day, String openingTime, String closingTime){
        day[0] = openingTime;
        day[1] = closingTime;
    }

    public int increaseWait(){
        this.wait+= 15;
        return wait;
    } //Method called whenever an appointment is booked (fixed 15 min addition)

    public int decreaseWait(){
        this.wait = this.wait - 15;
        return wait;
    }

    public void addService(Service s){
        servicesOffered.add(s);
    }

    public void removeService(Service s){
        servicesOffered.remove(s);
    }

    public void addCheckedIn(String p){
        checkedInList.add(p);
    }

    public void removeCheckedIn(String p){
        checkedInList.remove(p);
    }

    public ArrayList<String> getCheckedIn() {
        return checkedInList;
    }

    public int getNum(){return review_num;}
    public double getRate(){
        return rate;
    }
    public double averageRate(double new_rate){
        review_num+=1;
        return rate =((rate*review_num)+new_rate )/(review_num+1) ;
    }
}
