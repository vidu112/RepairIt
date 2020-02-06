package com.example.repairit;

import java.util.Date;

public class Customer {
    private String FullName;
    private String PhoneNo;
    private String Email;
    private String DateOfBirth;

    public Customer(String fullName, String phoneNo, String email, String dateOfzbirth) {
        this.FullName = fullName;
        this.PhoneNo = phoneNo;
        this.Email = email;
        this.DateOfBirth = dateOfzbirth;
    }

    public String getFullName() {
        return FullName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public String getDateOfzbirth() {
        return DateOfBirth;
    }
}
