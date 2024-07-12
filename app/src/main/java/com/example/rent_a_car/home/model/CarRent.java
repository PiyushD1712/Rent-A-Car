package com.example.rent_a_car.home.model;

public class CarRent {
    private String id;
    private String carName;
    private int price;
    private String description;
    private String imgUrl;
    private String ownerId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
