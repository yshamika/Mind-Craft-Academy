package com.example.myapplication.User;

public class User {
    private String nic;
    private String name;
    private String email;
    private String mobileNo;
    private String dob;
    private String address;
    private String livingCity;
    private String gender;
    private byte[] image;

    public User(String nic, String name, String email, String mobileNo, String dob,
                String address, String livingCity, String gender, byte[] image) {
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.dob = dob;
        this.address = address;
        this.livingCity = livingCity;
        this.gender = gender;
        this.image = image;
    }

    // Getters and setters
    // Omitted for brevity


    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
