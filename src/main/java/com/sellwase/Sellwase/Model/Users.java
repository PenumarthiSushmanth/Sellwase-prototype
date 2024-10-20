package com.sellwase.Sellwase.Model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;
import org.springframework.http.StreamingHttpOutputMessage;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;
    private String uname;
    @Column(unique = true)
    private String uemail;
    private String pass;
    private String uPhoneNum;
    private String location;
    private int ratingCount;
    private float userRating;
    private String profilePic;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getuPhoneNum() {
        return uPhoneNum;
    }

    public void setuPhoneNum(String uPhoneNum) {
        this.uPhoneNum = uPhoneNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
