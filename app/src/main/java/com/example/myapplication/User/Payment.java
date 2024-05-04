package com.example.myapplication.User;
public class Payment {
    private double cost;
    private String courseName;
    private String userName;

    public Payment(double cost, String courseName, String userName) {
        this.cost = cost;
        this.courseName = courseName;
        this.userName = userName;
    }

    public double getCost() {
        return cost;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getUserName() {
        return userName;
    }
}
