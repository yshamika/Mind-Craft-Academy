package com.example.myapplication.Course;
public class Course {
    private long id;
    private String C_name;
    private String cost;
    private String startDate;
    private String regiEndDate;
    private String duration;
    private String maxNum;
    private String locations;

    public Course(long id, String C_name, String cost, String startDate, String regiEndDate, String duration, String maxNum, String locations) {
        this.id = id;
        this.C_name = C_name;
        this.cost = cost;
        this.startDate = startDate;
        this.regiEndDate = regiEndDate;
        this.duration = duration;
        this.maxNum = maxNum;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getC_Name() {
        return C_name;
    }

    public void setName(String C_name) {
        this.C_name = C_name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRegiEndDate() {
        return regiEndDate;
    }

    public void setRegiEndDate(String regiEndDate) {
        this.regiEndDate = regiEndDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", C_name='" + C_name + '\'' +
                ", cost='" + cost + '\'' +
                ", startDate='" + startDate + '\'' +
                ", regiEndDate='" + regiEndDate + '\'' +
                ", duration='" + duration + '\'' +
                ", maxNum='" + maxNum + '\'' +
                ", locations='" + locations + '\'' +
                '}';
    }
}
