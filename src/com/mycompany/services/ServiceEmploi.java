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
import com.mycompany.entities.Emploi;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eslem Nabitt
 */
public class ServiceEmploi {
    boolean resultOk;
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
    
    //display publications 
    
    public ArrayList<Emploi> displayPubs(){
        
        ArrayList<Emploi> response = new ArrayList<>();
        String url = Static.BASE_URL+"/allemploisJSON";
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent event) {
                JSONParser jsonParser = new JSONParser();
                try{
                    Map<String,Object> mapPubs = jsonParser.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapPubs.get("root");
                    
                    for(Map<String,Object> object : ListOfMaps){
                        Emploi pub = new Emploi();
                        Result result = Result.fromContent(object);
                        //id
                        float id = Float.parseFloat(object.get("id").toString());
                        //title
                        String title = object.get("title").toString();
                        //content
                        String content = object.get("content").toString();
                        //category
                        pub.setCategoryName(result.getAsString("categoryName/categoryName"));
                        //image            
                        pub.setImage(result.getAsString("image"));
                        pub.setId((int) id);
                        pub.setTitle(title);
                        pub.setContent(content);
                        //convert date into date format
                        String DateConv = object.get("date").toString().substring(object.get("date").toString().indexOf("timestamp") + 1,object.get("date").toString().lastIndexOf("T"));
                        pub.setDate(DateConv);
                        response.add(pub);
                    }
                } catch (IOException ex) {
                    System.out.print(ex);
                }

            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return response;
    }
     // add publication 
    public boolean addPublication(Emploi pub){ 
        String url = Static.BASE_URL+"/addemploiJSON/new?title="+ pub.getTitle()+ "&content=" + pub.getContent() + "&categoryName=" + pub.getCategoryName()+ "&image=" + pub.getImage();
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
    
    //update publication
     public boolean updateOffre(int id,String title, String content, String categoryName, String image) {
            String url = Static.BASE_URL + "/updateemploiJSON/"+id + "?title=" + title + "&content=" + content + "&categoryName=" + categoryName + "&image=" + image;
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
    //delete publication 
     
    public boolean deleteOffre(int id) {
        String url = Static.BASE_URL + "/deleteemploiJSON/" + id;
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
