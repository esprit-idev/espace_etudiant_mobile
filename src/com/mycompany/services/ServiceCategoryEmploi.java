/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.CategoryEmploi;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eslem Nabitt
 */
public class ServiceCategoryEmploi {
    
    boolean resultOk;
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
    
     //display publications 
    
    public ArrayList<CategoryEmploi> displayCats(){
        
        ArrayList<CategoryEmploi> response = new ArrayList<>();
        String url = Static.BASE_URL+"/allcategoriesEmploiJSON";
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                JSONParser jsonParser = new JSONParser();
                try{
                    Map<String,Object> mapPubs = jsonParser.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapPubs.get("root");
                    
                    for(Map<String,Object> object : ListOfMaps){
                        CategoryEmploi cat = new CategoryEmploi();
                        //category
                        String categoryName =object.get("categoryName").toString();
                        cat.setCategoryName(categoryName);
                        response.add(cat);
                    }
                } catch (IOException ex) {
                    System.out.print(ex);
                }

            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return response;
    }
    //add
        public boolean addCategory(CategoryEmploi pub){ 
        String url = Static.BASE_URL+"/addcatEmploiJSON/new?categoryName="+ pub.getCategoryName();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200;
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;
    }
     //update 
    public boolean updateCategory(int id , String catName) {
        String url = Static.BASE_URL + "/updatecatEmploiJSON/"+id+"?categoryName="+catName;
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200;

                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return resultOk;
    }
    
    //delete 
     public boolean deleteCategory(int id) {
        String url = Static.BASE_URL + "/deletecatEmploiJSON/"+id;
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = request.getResponseCode() == 200;

                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOk;

    }

}
