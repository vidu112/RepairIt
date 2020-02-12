package com.example.repairit;

public class HiresClass {
    private String CustomerName;
    private String Price;
    private String Date;
    private String RepairmanName;
    private String RepairmanEmail;
    private String RepairmanType;
    private String Description;
    private String Status;
    private String HireLocLat;
    private String HireLocLon;
    private String RepairmanID;
    private String CustomerID;

    public HiresClass(String customerName, String customerPaidPrice, String date, String repairmanID, String repairmanName, String repairmanEmail, String repairmanType, String description, String status, String hireLocLat, String hireLocLon, String customerID)
        {

            CustomerName = customerName;
            Price = customerPaidPrice;
            Date = date;
            RepairmanID = repairmanID;
            RepairmanName = repairmanName;
            RepairmanEmail = repairmanEmail;
            RepairmanType = repairmanType;
            Description = description;
            Status = status;
            HireLocLat = hireLocLat;
            HireLocLon = hireLocLon;
            CustomerID = customerID;
        }


    public String getStatus() {
        return Status;
    }

    public String getRepairmanID() {
        return RepairmanID;
    }

    public String getCustomerID() {
        return CustomerID;
    }
    public String getHireLocLat() {
        return HireLocLat;
    }

    public String getHireLocLon() {
        return HireLocLon;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getCustomerPaidPrice() {
        return Price;
    }

    public String getDate() {
        return Date;
    }

    public String getRepairmanName() {
        return RepairmanName;
    }

    public String getRepairmanEmail() {
        return RepairmanEmail;
    }


    public String getRepairmanType() {
        return RepairmanType;
    }

    public String getDescription() {
        return Description;
    }


}
