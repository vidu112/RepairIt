package com.example.repairit;

import com.google.common.util.concurrent.FutureCallback;

import io.opencensus.common.Function;

public class Repairmen {

    private String fullName;
    private String repairType;
    private String rating;
    private String costPerDay;
    private String description;
    //private Function Hire;

    public Repairmen() {}

    public Repairmen(String fullName, String repairType, String description, String rating, String costPerDay) {
        this.fullName = fullName;
        this.costPerDay = costPerDay;
        this.repairType = repairType;
        this.rating = rating;
        this.description = description;
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
    public String getCostPerDay(){
        return costPerDay;
    }

    public String getDescription(){
        return description;
    }

}
