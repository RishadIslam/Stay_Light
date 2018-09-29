package com.example.rishad.stay_light;

public class HostPlaceInfo {
    String userId,guestNumber,location,houseTypeItem,accoType,privateBath,noOfbed,noOfBath,apartmentNo,shouseNo,sroadNo,scityName,szipCode,
            amenities;


    public HostPlaceInfo () {}

    public HostPlaceInfo(String userId, String guestNumber, String location, String houseTypeItem, String accoType, String privateBath, String noOfbed, String noOfBath, String apartmentNo, String shouseNo, String sroadNo, String scityName, String szipCode, String amenities) {
        this.userId = userId;
        this.guestNumber = guestNumber;
        this.location = location;
        this.houseTypeItem = houseTypeItem;
        this.accoType = accoType;
        this.privateBath = privateBath;
        this.noOfbed = noOfbed;
        this.noOfBath = noOfBath;
        this.apartmentNo = apartmentNo;
        this.shouseNo = shouseNo;
        this.sroadNo = sroadNo;
        this.scityName = scityName;
        this.szipCode = szipCode;
        this.amenities = amenities;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(String guestNumber) {
        this.guestNumber = guestNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouseTypeItem() {
        return houseTypeItem;
    }

    public void setHouseTypeItem(String houseTypeItem) {
        this.houseTypeItem = houseTypeItem;
    }

    public String getAccoType() {
        return accoType;
    }

    public void setAccoType(String accoType) {
        this.accoType = accoType;
    }

    public String getPrivateBath() {
        return privateBath;
    }

    public void setPrivateBath(String privateBath) {
        this.privateBath = privateBath;
    }

    public String getNoOfbed() {
        return noOfbed;
    }

    public void setNoOfbed(String noOfbed) {
        this.noOfbed = noOfbed;
    }

    public String getNoOfBath() {
        return noOfBath;
    }

    public void setNoOfBath(String noOfBath) {
        this.noOfBath = noOfBath;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getShouseNo() {
        return shouseNo;
    }

    public void setShouseNo(String shouseNo) {
        this.shouseNo = shouseNo;
    }

    public String getSroadNo() {
        return sroadNo;
    }

    public void setSroadNo(String sroadNo) {
        this.sroadNo = sroadNo;
    }

    public String getScityName() {
        return scityName;
    }

    public void setScityName(String scityName) {
        this.scityName = scityName;
    }

    public String getSzipCode() {
        return szipCode;
    }

    public void setSzipCode(String szipCode) {
        this.szipCode = szipCode;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
}
