/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author anash
 */
public class ClubPub {

    String id,pubDesc, pubImage, pubFile, pubDate, Club;

    public ClubPub() {
    }

    public ClubPub(String id, String pubDesc, String pubImage, String pubFile, String pubDate, String Club) {
        this.id = id;
        this.pubDesc = pubDesc;
        this.pubImage = pubImage;
        this.pubFile = pubFile;
        this.pubDate = pubDate;
        this.Club = Club;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getPubDesc() {
        return pubDesc;
    }

    public void setPubDesc(String pubDesc) {
        this.pubDesc = pubDesc;
    }

    public String getPubImage() {
        return pubImage;
    }

    public void setPubImage(String pubImage) {
        this.pubImage = pubImage;
    }

    public String getPubFile() {
        return pubFile;
    }

    public void setPubFile(String pubFile) {
        this.pubFile = pubFile;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String Club) {
        this.Club = Club;
    }
    

}
