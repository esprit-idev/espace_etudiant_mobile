/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author aa
 */
public class User {
         private int id ;
    private String username,prenom,email,password , image , roles;
    private boolean isBanned ;
    
    public User(int id,String username ,String prenom , String email ,String password , boolean isBanned , String image , String roles){
        this.id=id ;
        this.username=username ;
        this.prenom=prenom ;
        this.email=email;
        this.password=password;
        this.isBanned=isBanned;
        this.image=image ;
        this.roles=roles ;
    }
    public User(String username ,String prenom , String email ,String password ){
      ;
        this.username=username ;
        this.prenom=prenom ;
        this.email=email;
        this.password=password;
    }
    
    
     public User(String username  ,String password ){
      ;
        this.username=username ;
        this.password=password;
    }
    public User(){
    }

   // public User(String toString, String toString0, String toString1, String toString2) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    public int getId(){
        return id ;
    }
    public void setId(int id){
        this.id=id ;
    }
    public String getUsername(){
        return username ;
    }
    public void setUsername(String username){
        this.username=username ;
    }
    public String getPrenom(){
        return prenom ;
    }
    public void setPrenom(String prenom){
        this.prenom=prenom ;
    }
     public String getEmail(){
        return email ;
    }
    public void setEmail(String email){
        this.email=email ;
    }
     public String getPassword(){
        return password ;
    }
    public void setPassword(String password){
        this.password=password ;
    }
     public String getRoles(){
        return roles ;
    }
    public void setRoles(String role){
        this.roles=roles ;
    }
     public boolean getIsBanned(){
        return isBanned ;
    }
    public void setIsBanned(boolean isBanned){
        this.isBanned=isBanned ;
    }

    
}
