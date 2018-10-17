package com.example.rishad.stay_light;

import java.util.Date;

public class BookingHouse {
    String ownerId,bookedBy;
    int adults,children;
    Date checkIn,checkOut;

    BookingHouse(){}

    public BookingHouse(String ownerId, String bookedBy, int adults, int children, Date checkIn, Date checkOut) {
        this.ownerId = ownerId;
        this.bookedBy = bookedBy;
        this.adults = adults;
        this.children = children;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }
}
