/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.uis;

import com.codename1.io.Preferences;

/**
 *
 * @author YOOSURF
 */
public class SessionManager {
    
      public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int id ; 
    private static String userName ; 
    private static String prenom ; 
    private static String email; 
    private static String passowrd ;
    private static String image;
    private static String roles;
    private static Boolean isBanned;


    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id",id);// kif nheb njib id user connecté apres njibha men pref 
    }

    public static void setId(int id) {
        pref.set("id",id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

    public static String getUserName() {
        return pref.get("username",userName);
    }

    public static void setUserName(String userName) {
         pref.set("username",userName);
    }
    
     public static String getPrenom() {
        return pref.get("prenom",prenom);
    }

    public static void setPrenom(String prenom) {
         pref.set("prenom",prenom);
    }

    public static String getEmail() {
        return pref.get("email",email);
    }

    public static void setEmail(String email) {
         pref.set("email",email);
    }
    public static String getRoles() {
        return pref.get("roles",roles);
    }

    public static void setRoles(String roles) {
         pref.set("roles",roles);
    }

    public static String getPassowrd() {
        return pref.get("passowrd",passowrd);
    }

    public static void setPassowrd(String passowrd) {
         pref.set("passowrd",passowrd);
    }

    public static String getImage() {
        return pref.get("image",image);
    }

    public static void setImage(String image) {
         pref.set("image",image);
    }
     public static Boolean getIsBanned() {
        return pref.get("isBanned",isBanned);
    }

    public static void setIsBanned(Boolean isBanned) {
         pref.set("isBanned",isBanned);}
    
    
}
