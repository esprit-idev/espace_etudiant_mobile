/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;

/**
 *
 * @author Eslem Nabitt
 */
public class ServiceEmploi {
    
    public static ServiceEmploi instance = null;
    
    public ConnectionRequest request;
    
    public static ServiceEmploi getInstance(){
        if(instance == null)
            instance = new ServiceEmploi();
        return instance;
    }
    //constructor
    private ServiceEmploi(){
        request = new ConnectionRequest();
    }
}
