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
public class ServiceCategoryEmploi {
    
    public static ServiceCategoryEmploi instance = null;
    
    public ConnectionRequest request;
    
    public static ServiceCategoryEmploi getInstance(){
        if(instance == null)
            instance = new ServiceCategoryEmploi();
        return instance;
    }
    //constructor
    private ServiceCategoryEmploi(){
        request = new ConnectionRequest();
    }
}
