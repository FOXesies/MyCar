package com.example.mycar.Model;

public class Order {
    private String time;
    private String productName;
    private String category;
    private String productPrice;
    private String status;
    private String phone;
    private String nameUser;
    private String productId;

    public Order() {
    }

    public Order(String time, String productName, String category, String productPrice, String status, String phone, String nameUser, String productId) {
        this.time = time;
        this.productName = productName;
        this.category = category;
        this.productPrice = productPrice;
        this.status = status;
        this.phone = phone;
        this.nameUser = nameUser;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
