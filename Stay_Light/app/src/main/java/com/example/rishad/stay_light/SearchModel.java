package com.example.rishad.stay_light;

public class SearchModel {
    String Title, Image;

    public SearchModel() {}

    public SearchModel(String title, String image, String hDescription) {
        Title = title;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
