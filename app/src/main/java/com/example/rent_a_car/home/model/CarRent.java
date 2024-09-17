package com.example.rent_a_car.home.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

public class CarRent {
    private String id;
    private String carName;
    private int price;
    private String description;
    private String imgUrl;
    private String ownerId;
    @ServerTimestamp
    private Timestamp timestamp;

    public CarRent() {
    }

    public CarRent(String id, String carName, int price, String description, String imgUrl, String ownerId) {
        this.id = id;
        this.carName = carName;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.ownerId = ownerId;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CarRent{" +
                "id='" + id + '\'' +
                ", carName='" + carName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }
}
