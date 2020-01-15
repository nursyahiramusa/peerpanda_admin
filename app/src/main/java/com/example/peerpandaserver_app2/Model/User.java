package com.example.peerpandaserver_app2.Model;

public class User {
    private String AdminID;
    private String Pass;
    private String Name;
    private String PhoneNo;
    private String isStaff;

    public User() {
    }

    public User(String adminID, String pass, String name, String phoneNo, String isStaff) {
        AdminID = adminID;
        Pass = pass;
        Name = name;
        PhoneNo = phoneNo;
        this.isStaff = isStaff;
    }

    public String getAdminID() {
        return AdminID;
    }

    public void setAdminID(String adminID) {
        AdminID = adminID;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    @Override
    public String toString() {
        return "User{}";
    }
}
