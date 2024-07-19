package com.example.rent_a_car.home.model;

import org.jetbrains.annotations.NotNull;

public class Bookings {

    private String bookingId;
    private String renterId;
    private String carId;
    private String startDate;
    private String endDate;

    public Bookings() {
    }

    public Bookings(String bookingId, String renterId, String carId, String startDate, String endDate) {
        this.bookingId = bookingId;
        this.renterId = renterId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @NotNull
    @Override
    public String toString() {
        return "Bookings{" +
                "bookingId='" + bookingId + '\'' +
                ", renterId='" + renterId + '\'' +
                ", carId='" + carId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
