package com.example.repairit;

import com.google.common.util.concurrent.FutureCallback;

import io.opencensus.common.Function;

public class Repairman {

    private String fullName;
    private String repairType;
    private String rating;
    private String costPerDay;
    private String description;
    private String email;
    private String id;
    //private Function Hire;

    public Repairman() {}

    public Repairman(String fullName, String repairType, String description, String rating, String costPerDay, String email, String id) {
        this.fullName = fullName;
        this.costPerDay = costPerDay;
        this.repairType = repairType;
        this.rating = rating;
        this.description = description;
        this.email = email;
        this.id = id;
        //this.Hire = Hire;
    }

    public String getFullName(){
        return fullName;
    }
    public String getRepairType(){
        return repairType;
    }
    public String getRating(){
        return rating;
    }

    public String getId() {
        return id;
    }

    public String getCostPerDay(){
        return costPerDay;
    }

    public String getDescription(){
        return description;
    }

    public String getEmail() {
        return email;
    }
}
