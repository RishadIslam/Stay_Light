package com.example.rishad.stay_light;

public class HouseCoordinates {
    private double mHouseLatitude;

    public double getmHouseLatitude() {
        return mHouseLatitude;
    }

    public void setmHouseLatitude(double mHouseLatitude) {
        this.mHouseLatitude = mHouseLatitude;
    }

    public double getmHouseLongitude() {
        return mHouseLongitude;
    }

    public void setmHouseLongitude(double mHouseLongitude) {
        this.mHouseLongitude = mHouseLongitude;
    }

    public HouseCoordinates(double mHouseLatitude, double mHouseLongitude) {

        this.mHouseLatitude = mHouseLatitude;
        this.mHouseLongitude = mHouseLongitude;
    }

    private double mHouseLongitude;

    public HouseCoordinates () {

    }


}
