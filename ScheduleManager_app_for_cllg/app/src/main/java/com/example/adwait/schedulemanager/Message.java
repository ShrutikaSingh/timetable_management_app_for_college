package com.example.adwait.schedulemanager;

public class Message {
    String message, title, image;

    Message(String message, String title, String image)
    {
        this.message=message;
        this.title=title;
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {return image;}
}

