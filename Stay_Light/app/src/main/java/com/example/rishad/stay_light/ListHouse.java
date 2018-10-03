package com.example.rishad.stay_light;

public class ListHouse {
    String Title, Image;

    public ListHouse() {}

    public ListHouse(String title, String image) {
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
