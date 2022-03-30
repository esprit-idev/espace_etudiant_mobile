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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.CategoryNews;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eslem Nabitt
 */
public class ServiceCategoryNews {
    boolean resultOk;
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
    
     //display publications 
    
    public ArrayList<CategoryNews> displayCats(){
        
        ArrayList<CategoryNews> response = new ArrayList<>();
        String url = Static.BASE_URL+"/allcategoriesnewsJSON";
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                JSONParser jsonParser = new JSONParser();
                try{
                    Map<String,Object> mapPubs = jsonParser.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapPubs.get("root");
                    
                    for(Map<String,Object> object : ListOfMaps){
                        CategoryNews cat = new CategoryNews();
                        //id
                        float id = Float.parseFloat(object.get("id").toString());
                        //category
                        String categoryName =object.get("categoryName").toString();
                        cat.setId((int) id);
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
    public boolean addCategory(CategoryNews pub){ 
        String url = Static.BASE_URL+"/addcategorynewsJSON/new?categoryName="+ pub.getCategoryName();
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
        String url = Static.BASE_URL + "/updatecatNewsJSON/" + id + "?categoryName=" + catName;
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
        String url = Static.BASE_URL + "/deletecatNewsJSON/" + id;
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
