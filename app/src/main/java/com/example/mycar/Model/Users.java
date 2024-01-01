package com.example.mycar.Model;

public class Users {
    private String name, phone, pass, address, image;
    private Boolean permissions;

    public Boolean getPermissions() {
        return permissions;
    }

    public void setPermissions(Boolean permissions) {
        this.permissions = permissions;
    }

    public Users() {}

    public Users(String name, String pass, Boolean permissions, String address, String image) {
        this.name = name;
        this.pass = pass;
        this.address = address;
        this.permissions = permissions;
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pass='" + pass + '\'' +
                ", image='" + image + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}

