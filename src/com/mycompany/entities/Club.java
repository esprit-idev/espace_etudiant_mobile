/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

/**
 *
 * @author anash
 */
public class Club {
    String clubId,clubName,clubPic,clubDesc,clubRespo,clubCategorie;

    
    public Club() {
	
}
    public Club(String clubId,String clubName, String clubPic, String clubDesc, String clubRespo, String clubCategorie) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubPic = clubPic;
        this.clubDesc = clubDesc;
        this.clubRespo = clubRespo;
        this.clubCategorie = clubCategorie;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubPic() {
        return clubPic;
    }

    public void setClubPic(String clubPic) {
        this.clubPic = clubPic;
    }

    public String getClubDesc() {
        return clubDesc;
    }

    public void setClubDesc(String clubDesc) {
        this.clubDesc = clubDesc;
    }

    public String getClubRespo() {
        return clubRespo;
    }

    public void setClubRespo(String clubRespo) {
        this.clubRespo = clubRespo;
    }

    public String getClubCategorie() {
        return clubCategorie;
    }

    public void setClubCategorie(String clubCategorie) {
        this.clubCategorie = clubCategorie;
    }
    
    
    
}
