package com.example.mycar.Model;

import com.example.mycar.Enum.CarType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Car {

    private SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");

    private SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
    private CarType category;
    private String date;
    private String time;

    private String description;
    private String key;
    private String productName;
    private String productPrice;
    private String imageUrl;

    public Car() {}

    public Car(SimpleDateFormat currentDate, SimpleDateFormat currentTime, String category, String date, String time, String description, String key, String productName, String productPrice, String imageUrl) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.category = CarType.valueOfCar(category);
        this.date = date;
        this.time = time;
        this.description = description;
        this.key = key;
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
    }

    public SimpleDateFormat getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(SimpleDateFormat currentDate) {
        this.currentDate = currentDate;
    }

    public SimpleDateFormat getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(SimpleDateFormat currentTime) {
        this.currentTime = currentTime;
    }

    public CarType getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = CarType.valueOfCar(category);;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
