package com.example.androidblogapp.Models;
import com.google.firebase.database.ServerValue;

public class Post {

    private String title;
    private String desc;
    private String imageUrl;
    private String uid;
    private String userName;
    private Object timeStamp ;

    private String userPhoto;



    public Post(String title, String desc, String imageUrl, String uid, String userName, String userPhoto) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.uid = uid;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.timeStamp = ServerValue.TIMESTAMP;

    }
    public Post() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public Object getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
