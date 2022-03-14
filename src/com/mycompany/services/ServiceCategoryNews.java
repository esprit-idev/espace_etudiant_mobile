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
public class ServiceCategoryNews {
    
    public static ServiceCategoryNews instance = null;
    
    public ConnectionRequest request;
    
    public static ServiceCategoryNews getInstance(){
        if(instance == null)
            instance = new ServiceCategoryNews();
        return instance;
    }
    //constructor
    private ServiceCategoryNews(){
        request = new ConnectionRequest();
    }
}
