package com.example.rishad.stay_light;

public class SearchModel {
    String id, url;
    private transient String houseID;

    public SearchModel() {}

    public SearchModel(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getid() {
        return id;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
